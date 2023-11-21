package cn.chendahai.config;

import io.lettuce.core.ReadFrom;
import io.lettuce.core.internal.LettuceLists;
import io.lettuce.core.models.role.RedisInstance;
import io.lettuce.core.models.role.RedisNodeDescription;

import java.util.List;

/**
 * @author Dylan
 */
public class ReadFromCustom extends ReadFrom {

    private static final ThreadLocal<Boolean> contextHolder = new InheritableThreadLocal<>();

    /**
     * 设置数据源
     */
    public static void readMaster() {
        contextHolder.set(true);
    }

    /**
     * 取得当前数据源
     *
     * @return
     */
    public static Boolean getDataSource() {
        return contextHolder.get();
    }

    /**
     * 清除上下文数据
     */
    public static void clear() {
        contextHolder.remove();
    }

    @Override
    public List<RedisNodeDescription> select(Nodes nodes) {

        Boolean readMaster = contextHolder.get();

        List<RedisNodeDescription> nodeList = nodes.getNodes();
//        System.out.println(">>>>>>>>>>>>nodeList:" + nodeList);

        // 读主
        if (readMaster != null && readMaster) {
            System.out.println(">>>>>>>>>>>>读取master");
            for (RedisNodeDescription node : nodeList) {
                if (RedisInstance.Role.MASTER.equals(node.getRole())) {
                    System.out.println(Thread.currentThread().getName() + ">>>>>>>>>>>>读取的节点：" + node);
                    return LettuceLists.newList(node);
                }
            }
        }
        System.out.println(">>>>>>>>>>>>进行随机读取");
        return nodeList;
        // 随机读
//        int random = RandomUtil.random(0, nodeList.size() - 1);
//        int random = (int) (Math.random() * nodeList.size());
//        System.out.println(Thread.currentThread().getName() + ">>>>>>>>>>>>随机读：" + random);
//        System.out.println(">>>>>>>>>>>>读取的节点：" + nodeList.get(random));
//        return LettuceLists.newList(nodeList.get(random));
    }

    public static void main(String[] args) {
        /*
        (数据类型)(最小值+Math.random()*(最大值-最小值+1))例:
        (int)(1+Math.random()*(10-1+1))
        从1到10的int型随数
         */
        for (int i = 0; i < 10; i++) {
            int random = (int) (Math.random() * 3);
            System.out.println(random);
        }
    }


}
