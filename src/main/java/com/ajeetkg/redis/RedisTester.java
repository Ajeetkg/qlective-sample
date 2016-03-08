package com.ajeetkg.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created by agupta2 on 3/4/16.
 */
public class RedisTester {


    @Autowired
    private RedisTemplate<String, String> template;

  /*  public static void main(String[] args){

        RedisTester redisTester = new RedisTester();
        try{
            redisTester.testRedis();
        }catch(Exception e){
                          e.printStackTrace();
        }

    }
*/
    public void testRedis() throws Exception{

        String key= "psToken1";


        System.out.println();

    }


}
