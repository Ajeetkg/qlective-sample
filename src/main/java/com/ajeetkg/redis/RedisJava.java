package com.ajeetkg.redis;

/**
 * Created by agupta2 on 3/4/16.
 */
public class RedisJava {

/*

    public static void main(String[] args){

        Jedis jedis = new Jedis("localhost");
        System.out.println("Connection to server successful");

        //Check whethere the connection is successful or not
        System.out.println("Server is running:  " + jedis.ping());

        RedisJava redisJava = new RedisJava();


        String tokenKey = "psToken5";
        redisJava.deleteJedisKeys(tokenKey, jedis);

        redisJava.storelist(tokenKey,jedis);





    }

    private void saveToken(String token){

        Jedis jedis = new Jedis("localhost");
        jedis.set(token, "");

    }
    private void storelist(String tokenKey,Jedis jedis){

        RedisJava redisJava = new RedisJava();
        jedis.lpush(tokenKey, redisJava.getUUID());
        jedis.lpush(tokenKey, redisJava.getUUID());
        jedis.lpush(tokenKey, redisJava.getUUID());
        jedis.lpush(tokenKey, redisJava.getUUID());
        jedis.lpush(tokenKey, redisJava.getUUID());

        List<String> list = jedis.lrange(tokenKey, 0, 5);
        for(int i=0; i<list.size(); i++) {
            System.out.println("List of stored keys:: "+list.get(i));
        }

    }

    private String getUUID(){
        String uUid = java.util.UUID.randomUUID()+"";
        //System.out.println(uUid);
        return uUid;
    }

    private void deleteJedisKeys(String keyPattern, Jedis jedis){
        Set<String> keys = jedis.keys(keyPattern);
        keys.stream().forEach(key -> deleteJedisKey(key, jedis));
    }
    private boolean deleteJedisKey(String key, Jedis jedis){
        jedis.del(key);
        return true;
    }*/
}
