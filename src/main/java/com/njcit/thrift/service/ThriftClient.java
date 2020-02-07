package com.njcit.thrift.service;

import com.njcit.thrift.generated.Person;
import com.njcit.thrift.generated.PersonService;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFastFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 * @Author LiJun
 * @Date 2020/2/7 15:53
 */

public class ThriftClient {
    public static void main(String[] args) {
        TTransport tTransport = new TFastFramedTransport(new TSocket("localhost",8899),600);
        TProtocol protocol = new TCompactProtocol(tTransport);
        PersonService.Client client = new PersonService.Client(protocol);

        try{
            tTransport.open();
            Person person = client.getPersonByUserName("张三");
            System.out.println(person.getUsername());
            System.out.println(person.getAge());
            System.out.println(person.isMarried());

            System.out.println("===========================");

            Person person1 = new Person();
            person1.setUsername("王五");
            person1.setAge(99);
            person1.setMarried(true);
            client.savePerson(person1);
        }
        catch (Exception e){
           throw new RuntimeException(e.getMessage(),e);
        }
        finally {
            tTransport.close();
        }
    }
}
