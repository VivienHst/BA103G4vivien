package serverEndpoint;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.msg.model.MsgService;

@ServerEndpoint("/MyEchoServer/{myName}/{urName}")
public class MyEchoServer {
private static final Map<Set<String>,  Set<Session>> pairSessions = Collections.synchronizedMap(new HashMap<Set<String>, Set<Session>>());
	
	@OnOpen
	public void onOpen(@PathParam("myName") String myName, @PathParam("urName") String urName, Session userSession) throws IOException {

		//key
		Set<String> pairSet=  Collections.synchronizedSet(new HashSet<String>());
		pairSet.add(myName);
		pairSet.add(urName);
		
		//first time of this key
		if(pairSessions.get(pairSet)==null){
			//val
			Set<Session> setSessions = Collections.synchronizedSet(new HashSet<Session>());
			setSessions.add(userSession);
			//map
			pairSessions.put(pairSet, setSessions);
		//others time of this key	
		} else{
			//val
			Set<Session> setSessions= pairSessions.get(pairSet);
			setSessions.add(userSession);
			//map
			pairSessions.put(pairSet, setSessions);
		}
	
		System.out.println(userSession.getId() + ": 已連線");
		System.out.println(myName + ": myName");
		System.out.println(urName + ": urName");
//		userSession.getBasicRemote().sendText("WebSocket 連線成功");
	}

	
	@OnMessage
	public void onMessage(@PathParam("myName") String myName, @PathParam("urName") String urName,Session userSession, String message) {
		//key
		Set<String> pairSet=  Collections.synchronizedSet(new HashSet<String>());
		pairSet.add(myName);
		pairSet.add(urName);
		//map
		for (Session session: pairSessions.get(pairSet)){
			if (session.isOpen())
				session.getAsyncRemote().sendText(message);
			
			Gson gson = new Gson();
			JsonObject jsonObject = gson.fromJson(message, JsonObject.class);
			String msg_cont = jsonObject.get("message").getAsString();
			
			MsgService msgSvc = new MsgService();
			msgSvc.addMsgVO(myName, urName, msg_cont);
		}
		
		System.out.println(pairSet+ "Message received: " + message);
	}
	
	@OnError
	public void onError(Session userSession, Throwable e){
//		e.printStackTrace();
	}
	
	@OnClose
	public void onClose(@PathParam("myName") String myName, @PathParam("urName") String urName, Session userSession, CloseReason reason) {
		//key
		Set<String> pairSet=  Collections.synchronizedSet(new HashSet<String>());
		pairSet.add(myName);
		pairSet.add(urName);
		//val
		Set<Session> setSessions= pairSessions.get(pairSet);
		setSessions.remove(userSession);
		//map
		pairSessions.put(pairSet, setSessions);
		
		System.out.println(userSession.getId() + pairSet+ ": Disconnected: " + Integer.toString(reason.getCloseCode().getCode()));
	}
}
