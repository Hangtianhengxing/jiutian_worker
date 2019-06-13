package kafka.kafka08;

import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.flume.source.avro.AvroFlumeEvent;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;


public class MyProducer implements Runnable {

    private static final String TOPIC = "dataflow_pressure_test"; //kafka创建的topic
//    private static final String TOPIC = "nsfsale_create_order"; //kafka创建的topic
//    private static String TOPIC ; //kafka创建的topic
        private final String BROKER_LIST = "kafkasit02broker01..com:9092,kafkasit02broker02..com:9092,kafkasit02broker03..com:9092";
//    private final String BROKER_LIST ="A:2181,B:2181,V:2181";
        //    private static String BROKER_LIST;
    private final String SERIALIZER_CLASS = "kafka.serializer.StringEncoder"; // 序列化类
//    private final String ZK_CONNECT = "A:2181,B:2181,C:2181";
    SpecificDatumWriter<AvroFlumeEvent> writer = new SpecificDatumWriter<AvroFlumeEvent>(AvroFlumeEvent.class);
    ByteArrayOutputStream tempOutStream = new ByteArrayOutputStream();
    BinaryEncoder encoder;
    String schemaString = "{\"type\":\"record\",\"name\":\"Event\",\"fields\":[{\"name\":\"headers\",\"type\":{\"type\":\"map\",\"values\":\"string\"}},{\"name\":\"body\",\"type\":\"int\"}]}";
    byte[] bytes;
    Logger logger = LoggerFactory.getLogger(MyProducer.class);
    private long number = 100000000000000L;
    Properties props;
    Producer<String, String> producer;
    private AtomicInteger value = new AtomicInteger(1);

    public MyProducer() {
        props = new Properties();
        props.put("acks", "all");
//        props.put("partitioner.class","kafka.test.kafka08.CidPartitioner");
//        props.put("zk.connect", ZK_CONNECT);
        props.put("serializer.class", SERIALIZER_CLASS);
        props.put("bootstrap.servers", BROKER_LIST);
        props.put("request.required.acks", "1");

        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//
//        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//        props.put("value.serializer", "org.apaclimithe.kafka.common.serialization.ByteArraySerializer");
        producer = new KafkaProducer<String, String>(props);
    }

    public void publishMessage(String topic, long count) {
        for (int i = 0; i < count; i++) {
            String runtime = new Date().toString();

            File file = new File("D:/WorkerCode/play_demo_work/Kafka-Test/src/lihao.txt");
//            File file = new File("/home/connect/install/lihao.txt");
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(file));
                String tempString = null;
                //一次读入一行，直到读入null为文件结束
                while ((tempString = reader.readLine()) != null) {
                    //显示行号
                    String msg = "line " + getValue() + ": " + tempString;
                    ProducerRecord<String, String> data = new ProducerRecord<String, String>(topic, msg);
                    producer.send(data);
                    System.out.println("msg = " + data.value());
                    increase();
                    Thread.sleep(100);
                }
//                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e1) {
                    }
                }
            }
        }
//        producer.close();
    }

    public void publishMessageOfOne(String topic, long count) {
//        String CONTENT = "{\"account_number\":\"000\",\"balance\":26210,\"firstname\":\"Teri\",\"lastname\":\"Hester\",\"age\":39,\"gender\":\"M\",\"address\":\"653 Abbey Court\",\"employer\":\"Electonic\",\"email\":\"terihester@electonic.com\",\"city\":\"Martell\",\"state\":\"LIHAO\"}";
        System.out.println("agasdaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        for (int i = 0; i < count; i++)
            try {
//                String CONTENT ="{\"name\":\"lihaolixuewei"+i+"\",\"sex\":\"female\",\"age\":20}";
//                String CONTENT ="name";
                String CONTENT ="{\"name\":\"es-lihao"+i+"\",\"sex\":\"female\",\"age\":20}";
//                String CONTENT ="NJXZ 10.27.116.155 192.168.65.253 10.49.44.169 2019-02-01T10:01:41+08:00 POST /center/open/sendMessage.do?isrvCode=isrv191 isrvCode=isrv191 80 HTTP/1.1 200 381 4.077 - Apache-HttpClient/4.2.6 (java 1.5) nimsit..com 10.27.116.155 10.37.104.180:80 4.077 - 1548986501.513 - - - - - - - 603 - - - - WAF2.3";
                System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbb");
                ProducerRecord<String, String> msg = new ProducerRecord(topic, CONTENT);
                System.out.println("cccccccccccccccccccccccccccccccccccccc");
                producer.send(msg);
                System.out.println("msg = " + getValue() + " : " + msg.value());
                increase();
            } catch (Exception e) {
                logger.info(e.getMessage());
            }
    }


    public void run() {
        try {
//            publishMessageOfOne(TOPIC, number);
                publishMessage(TOPIC, number);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getValue() {
        return value.get();
    }

    public int increase() {
        return value.incrementAndGet();
    }

    public int increase(int i) {
        return value.addAndGet(i);
    }

    public int decrease() {
        return value.decrementAndGet();
    }

    public int decrease(int i) {
        return value.addAndGet(-i);
    }
}