package com.njcit.thrift.service;

import com.njcit.thrift.generated.DataException;
import com.njcit.thrift.generated.Person;
import com.njcit.thrift.generated.PersonService;
import org.apache.thrift.TException;

/**
 * @Author LiJun
 * @Date 2020/2/7 15:43
 */

public class PersonServiceImpl implements PersonService.Iface {
    @Override
    public Person getPersonByUserName(String username) throws DataException, TException {
        System.out.println("getPersonByUserName invoked");
        Person person = new Person();
        person.setUsername("Àî¿¡ " + username);
        person.setAge(25);
        person.setMarried(false);
        return person;
    }

    @Override
    public void savePerson(Person person) throws DataException, TException {
        System.out.println("savePerson invoked");
        System.out.println(person.getUsername());
        System.out.println(person.getAge());
        System.out.println(person.isMarried());
    }
}
