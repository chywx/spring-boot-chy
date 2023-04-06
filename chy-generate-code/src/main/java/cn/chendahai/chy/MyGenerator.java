package cn.chendahai.chy;


import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * @author chen
 * @date 2018/7/20 10:33
 */
public class MyGenerator {

    private static String table = "bet_center";
//    private static String suffix = "log";
    /**
     * db
     */
    private static String url = "jdbc:mysql://192.168.50.81:3306/" + table + "?serverTimezone=CTT";
    private static String user = "root";
    private static String password = "ahthie5ioMaizi5aichieTh";
    private static String dirverName = "com.mysql.cj.jdbc.Driver";
    private static String author = "陈海洋";
    private static String outputDir = "D:\\generate-code";
    /**
     * 生成的东西放在这个包里
     */
    private static String packageName = "com.gbank.bet";

    public static void main(String[] args) {
        GlobalConfig config = new GlobalConfig();
        config.setBaseResultMap(true);
        String dbUrl = url;

        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
            .setUrl(dbUrl)
            .setUsername(user)
            .setPassword(password)
            .setDriverName(dirverName);

        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig
            .setCapitalMode(true)
            // 生成字段常量
            .setEntityColumnConstant(false)
            .setEntityLombokModel(false)
            .setDbColumnUnderline(true)
            .setNaming(NamingStrategy.underline_to_camel);

        config.setActiveRecord(false)
            .setEnableCache(false)
            .setAuthor(author)
            .setOutputDir(outputDir)
            .setFileOverride(true)
            .setServiceName("%sService");

        new AutoGenerator().setGlobalConfig(config)
            .setDataSource(dataSourceConfig)
            .setStrategy(strategyConfig)
            .setPackageInfo(
                new PackageConfig()
                    .setParent(packageName)
                    .setController("controller")
                    .setEntity("entity")
                    .setMapper("mapper")
                    .setServiceImpl("service.serviceImpl")
                    .setMapper("dao")
            ).execute();
    }
}