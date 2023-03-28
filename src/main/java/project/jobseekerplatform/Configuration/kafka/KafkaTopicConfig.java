//package project.jobseekerplatform.Configuration.kafka;
//
//import org.apache.kafka.clients.admin.NewTopic;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.config.TopicBuilder;
//
//@Configuration
//public class KafkaTopicConfig {
//    public static final String GROUP_ID = "comment-group";
//
//    @Bean
//    public NewTopic commentTopic() {
//        return TopicBuilder.name("comment-topic")
//                .build();
//    }
//
//    @Bean
//    public NewTopic postTopic() {
//        return TopicBuilder.name("post-topic")
//                .build();
//    }
//
//    @Bean
//    public NewTopic ReactTopic() {
//        return TopicBuilder.name("react-topic")
//                .build();
//    }
//
//
//}
