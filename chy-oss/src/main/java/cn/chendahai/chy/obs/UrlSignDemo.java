package cn.chendahai.chy.obs;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class UrlSignDemo {

    private static final String SIGN_SEP = "\n";

    private static final String OBS_PREFIX = "x-obs-";

    private static final String DEFAULT_ENCODING = "UTF-8";

    private static final List<String> SUB_RESOURCES = Collections.unmodifiableList(Arrays.asList(
            "CDNNotifyConfiguration", "acl", "append", "attname", "backtosource", "cors", "customdomain", "delete",
            "deletebucket", "directcoldaccess", "encryption", "inventory", "length", "lifecycle", "location", "logging",
            "metadata", "mirrorBackToSource", "modify", "name", "notification", "obscompresspolicy", "orchestration",
            "partNumber", "policy", "position", "quota", "rename", "replication", "response-cache-control",
            "response-content-disposition", "response-content-encoding", "response-content-language", "response-content-type",
            "response-expires", "restore", "storageClass", "storagePolicy", "storageinfo", "tagging", "torrent", "truncate",
            "uploadId", "uploads", "versionId", "versioning", "versions", "website", "x-image-process",
            "x-image-save-bucket", "x-image-save-object", "x-obs-security-token", "object-lock", "retention"));

    private String ak;

    private String sk;

    private boolean isBucketNameValid(String bucketName) {
        if (bucketName == null || bucketName.length() > 63 || bucketName.length() < 3) {
            return false;
        }

        if (!Pattern.matches("^[a-z0-9][a-z0-9.-]+$", bucketName)) {
            return false;
        }

        if (Pattern.matches("(\\d{1,3}\\.){3}\\d{1,3}", bucketName)) {
            return false;
        }

        String[] fragments = bucketName.split("\\.");
        for (int i = 0; i < fragments.length; i++) {
            if (Pattern.matches("^-.*", fragments[i]) || Pattern.matches(".*-$", fragments[i])
                    || Pattern.matches("^$", fragments[i])) {
                return false;
            }
        }

        return true;
    }

    public String encodeUrlString(String path) throws UnsupportedEncodingException {
        return URLEncoder.encode(path, DEFAULT_ENCODING)
                .replaceAll("\\+", "%20")
                .replaceAll("\\*", "%2A")
                .replaceAll("%7E", "~");
    }

    public String encodeObjectName(String objectName) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        String[] tokens = objectName.split("/");
        for (int i = 0; i < tokens.length; i++) {
            result.append(this.encodeUrlString(tokens[i]));
            if (i < tokens.length - 1) {
                result.append("/");
            }
        }
        return result.toString();
    }

    private String join(List<?> items, String delimiter) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            String item = items.get(i).toString();
            sb.append(item);
            if (i < items.size() - 1) {
                sb.append(delimiter);
            }
        }
        return sb.toString();
    }

    private boolean isValid(String input) {
        return input != null && !input.equals("");
    }

    public String hmacSha1(String input) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        SecretKeySpec signingKey = new SecretKeySpec(this.sk.getBytes(DEFAULT_ENCODING), "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(signingKey);
        return Base64.getEncoder().encodeToString(mac.doFinal(input.getBytes(DEFAULT_ENCODING)));
    }

    private String stringToSign(String httpMethod, Map<String, String[]> headers, Map<String, String> queries,
                                String bucketName, String objectName) throws Exception {
        String contentMd5 = "";
        String contentType = "";
        String date = "";

        TreeMap<String, String> canonicalizedHeaders = new TreeMap<String, String>();

        String key;
        List<String> temp = new ArrayList<String>();
        for (Map.Entry<String, String[]> entry : headers.entrySet()) {
            key = entry.getKey();
            if (key == null || entry.getValue() == null || entry.getValue().length == 0) {
                continue;
            }

            key = key.trim().toLowerCase(Locale.ENGLISH);
            if (key.equals("content-md5")) {
                contentMd5 = entry.getValue()[0];
                continue;
            }

            if (key.equals("content-type")) {
                contentType = entry.getValue()[0];
                continue;
            }

            if (key.equals("date")) {
                date = entry.getValue()[0];
                continue;
            }

            if (key.startsWith(OBS_PREFIX)) {

                for (String value : entry.getValue()) {
                    if (value != null) {
                        temp.add(value.trim());
                    }
                }

                canonicalizedHeaders.put(key, this.join(temp, ","));
                temp.clear();
            }
        }

        if (canonicalizedHeaders.containsKey("x-obs-date")) {
            date = "";
        }


        // handle method/content-md5/content-type/date
        StringBuilder stringToSign = new StringBuilder();
        stringToSign.append(httpMethod).append(SIGN_SEP)
                .append(contentMd5).append(SIGN_SEP)
                .append(contentType).append(SIGN_SEP)
                .append(date).append(SIGN_SEP);


        // handle canonicalizedHeaders
        for (Map.Entry<String, String> entry : canonicalizedHeaders.entrySet()) {
            stringToSign.append(entry.getKey()).append(":").append(entry.getValue()).append(SIGN_SEP);
        }


        // handle CanonicalizedResource
        stringToSign.append("/");
        if (this.isValid(bucketName)) {
            stringToSign.append(bucketName).append("/");
            if (this.isValid(objectName)) {
                stringToSign.append(this.encodeObjectName(objectName));
            }
        }

        TreeMap<String, String> canonicalizedResource = new TreeMap<String, String>();
        for (Map.Entry<String, String> entry : queries.entrySet()) {
            key = entry.getKey();
            if (key == null) {
                continue;
            }

            if (SUB_RESOURCES.contains(key)) {
                canonicalizedResource.put(key, entry.getValue());
            }
        }

        if (canonicalizedResource.size() > 0) {
            stringToSign.append("?");
            for (Map.Entry<String, String> entry : canonicalizedResource.entrySet()) {
                stringToSign.append(entry.getKey());
                if (this.isValid(entry.getValue())) {
                    stringToSign.append("=").append(entry.getValue());
                }
                stringToSign.append("&");
            }
            stringToSign.deleteCharAt(stringToSign.length() - 1);
        }
        //		System.out.println(String.format("StringToSign:%s%s", SIGN_SEP, stringToSign.toString()));

        return stringToSign.toString();
    }

    public String querySignature(String httpMethod, Map<String, String[]> headers, Map<String, String> queries,
                                 String bucketName, String objectName, long expires) throws Exception {
        if (!isBucketNameValid(bucketName)) {
            throw new IllegalArgumentException("the bucketName is illegal");
        }
        if (headers.containsKey("x-obs-date")) {
            headers.put("x-obs-date", new String[]{String.valueOf(expires)});
        } else {
            headers.put("date", new String[]{String.valueOf(expires)});
        }
        //1. stringToSign
        String stringToSign = this.stringToSign(httpMethod, headers, queries, bucketName, objectName);

        //2. signature
        return this.encodeUrlString(this.hmacSha1(stringToSign));
    }

    public String getURL(String endpoint, Map<String, String> queries,
                         String bucketName, String objectName, String signature, long expires) throws UnsupportedEncodingException {
        StringBuilder URL = new StringBuilder();
        URL.append("https://").append(bucketName).append(".").append(endpoint).append("/").
                append(this.encodeObjectName(objectName)).append("?");
        String key;
        for (Map.Entry<String, String> entry : queries.entrySet()) {
            key = entry.getKey();
            if (key == null) {
                continue;
            }
            if (SUB_RESOURCES.contains(key)) {
                String value = entry.getValue();
                URL.append(key);
                if (value != null) {
                    URL.append("=").append(value).append("&");
                } else {
                    URL.append("&");
                }
            }
        }
        URL.append("AccessKeyId=").append(this.ak).append("&Expires=").append(expires).
                append("&Signature=").append(signature);
        return URL.toString();
    }

    public static void main(String[] args) throws Exception {
        UrlSignDemo demo = new UrlSignDemo();
        demo.ak = "ak";
        demo.sk = "sk";
        String endpoint = "com";

        String bucketName = "packet";
        String objectName = "jar";

        // 若直接使用URL在浏览器地址栏中访问，无法带上头域，此处headers加入头域会导致签名不匹配，使用headers需要客户端处理
        Map<String, String[]> headers = new HashMap<String, String[]>();
        Map<String, String> queries = new HashMap<String, String>();

        // 请求消息参数Expires，设置24小时后失效
        long expires = (System.currentTimeMillis() + 10 * 1000L) / 1000;
//        long expires = (System.currentTimeMillis() + 86400000L) / 1000;
//        long expires = (System.currentTimeMillis() + 4000 * 86400000L) / 1000;
        String signature = demo.querySignature("GET", headers, queries, bucketName, objectName, expires);
        System.out.println(signature);
        String URL = demo.getURL(endpoint, queries, bucketName, objectName, signature, expires);
        System.out.println(URL);
    }
}

