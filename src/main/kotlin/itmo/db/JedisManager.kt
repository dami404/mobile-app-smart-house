package itmo.db

import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool

val jedis : Jedis = JedisPool().resource

suspend fun log(message : String) {
    jedis.publish("logger", message)
//    jedis.subscribe(object : JedisPubSub() {
//        override fun onMessage(channel : String, message : String) {
//            println(message)
//        }
//    }, "test")
}