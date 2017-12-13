package org.fzsoft.shoppingmall.utils.jms.rmq;//package org.songbai.mutual.utils.jms.rmq;
//
//import com.rabbitmq.jms.admin.RMQDestination;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.FactoryBean;
//
//import javax.jms.Destination;
//
///**
// * Created by yhj on 17/3/21.
// */
//public class TopicDestination implements FactoryBean<Destination> {
//
//    private String physicalName;
//
//    @Override
//    public Destination getObject() throws BeansException {
//
//        return new RMQDestination(physicalName,false,false);
//    }
//
//    @Override
//    public Class<?> getObjectType() {
//        return Destination.class;
//    }
//
//    @Override
//    public boolean isSingleton() {
//        return true;
//    }
//
//
//    public void setPhysicalName(String physicalName) {
//        this.physicalName = physicalName;
//    }
//}