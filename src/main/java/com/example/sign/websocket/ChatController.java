package com.example.sign.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;


    @RequestMapping("/chat/chatList")
    public String chatList(Model model){
        List<ChatRoom> roomList = chatService.findAllRoom();
        model.addAttribute("roomList",roomList);
        return "chatList";
    }


    @PostMapping("/chat/createRoom")  //방을 만들었으면 해당 방으로 가야지.
    public String createRoom(Model model, @RequestParam("user1") String user1, @RequestParam("user2") String user2) {
        ChatRoom room = chatService.createRoom(user1, user2);
        model.addAttribute("room",room);
        model.addAttribute("user1",user1);
        model.addAttribute("user2",user2);
        return "chatRoom";  //만든사람이 채팅방 1빠로 들어가게 됩니다
    }

    @GetMapping("/chat/chatRoom")
    public String chatRoom(Model model, @RequestParam("roomId") String roomId){
        ChatRoom room = chatService.findRoomById(roomId);
        model.addAttribute("room",room);   //현재 방에 들어오기위해서 필요한데...... 접속자 수 등등은 실시간으로 보여줘야 돼서 여기서는 못함
        return "chatRoom";
    }
}
