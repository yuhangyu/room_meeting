package meeting_room;

public class MeetingProtocol {

	public static final String MODE = ":";

	//ID, CHAT, CHATALL , MESSAGE, CHATLIST
	
	//(C->S) ID:aaa
	//(S->C) CHATLIST:aaa;bbb;ccc;ȫ�浿;
	public static final String ID = "ID";
	
	//(C->S) CHAT:�޴¾��̵�;�޼��� ex)CHAT:bbb;�����
	//(S->C) CHAT:�����¾��̵�;�޼��� ex)CHAT:aaa;�����
	public static final String ORDER = "ORDER";
	
	//(C->S) CHATALL:�޼���
	//(S->C) CHATALL:[�����¾��̵�]�޼���
	public static final String CHATALL = "CHATALL";
	
	//(C->S) MESSAGE:�޴¾��̵�;�������� ex)MESSAGE:bbb;�����
	//(S->C) MESSAGE:�����¾��̵�;�������� ex)MESSAGE:aaa;�����
	public static final String MESSAGE = "MESSAGE";
	
	//(S->C) CHATLIST:aaa;bbb;ccc;ȫ�浿;
	public static final String CHATLIST = "CHATLIST";
	
	
	
}





