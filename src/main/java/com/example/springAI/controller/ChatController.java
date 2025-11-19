//package controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import service.ChatService;
//
//@RestController
//@RequestMapping("/AI")
//public class ChatController {
//
//    @Autowired
//    private ChatService chatService;
//
//    @GetMapping("/chat/{prompt}")
//    public String chat(@PathVariable("prompt") String prompt){
//        return chatService.getChatResponse(prompt);
//    }
//
//}
