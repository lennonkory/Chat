package kcomp.chat.gui.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import kcomp.chat.common.entities.Client;
import kcomp.chat.common.entities.SocketProperties;
import kcomp.chat.common.enums.MessageType;
import kcomp.chat.common.listeners.Listener;
import kcomp.chat.common.listeners.RoomClientListener;
import kcomp.chat.common.listeners.RoomListener;
import kcomp.chat.common.messages.Message;

public class GuiRoom extends JFrame {

	private static final long serialVersionUID = 1L;

	// Panels
	private JPanel textPanel;
	private JPanel inputPanel;
	private JPanel clientListPanel;

	private JTextArea textArea;
	private JTextField textField;
	private JButton send;

	private JList<Object> clientList;
	private DefaultListModel<Object> listModel;
	private Client client;
	private String roomName;

	private SocketProperties props;

	public GuiRoom(String roomName, Object[] clientList, SocketProperties props, Client client) {
		super(roomName);
		this.roomName = roomName;
		this.setSize(400, 600);
		this.props = props;
		this.client = client;
		setUp(clientList);
	}

	private void createClient() {

		Listener<RoomListener> listener = new RoomClientListener();

		listener.setListener(new RoomListener() {

			@Override
			public void roomMessage(String message) {
				textArea.append(message + "\n");
			}

		});
		String topic = props.getTopics().get(1) + roomName;
		client.subscribe(topic);
		client.setListener(listener);
	}

	private void setUp(Object[] clientLlist) {

		this.clientListPanel = new JPanel();

		this.textPanel = new JPanel();
		this.textArea = new JTextArea();
		this.textArea.setEditable(false);
		this.textPanel.setLayout(new BorderLayout());

		JScrollPane scrollpane = new JScrollPane(textArea);

		this.textPanel.add(scrollpane);

		this.inputPanel = new JPanel();
		this.textField = new JTextField();
		this.textField.setColumns(20);

		this.inputPanel.add(textField);

		this.send = new JButton("Send");
		this.inputPanel.add(send);

		addListeners();

		createClientList(clientLlist);

		this.add(this.textPanel, BorderLayout.CENTER);
		this.add(this.inputPanel, BorderLayout.SOUTH);
		this.add(this.clientListPanel, BorderLayout.EAST);
		this.createClient();

	}

	private void createClientList(Object[] list) {

		listModel = new DefaultListModel<>();

		for (Object element : list) {
			listModel.addElement(element);
		}

		clientList = new JList<Object>(listModel);
		clientList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		clientList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		clientList.setVisibleRowCount(-1);

		JScrollPane listScroller = new JScrollPane(clientList);
		this.clientListPanel.add(listScroller);

	}

	private void addListeners() {
		this.send.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				client.send(props.getRoom() + "/" + roomName,
						new Message<String>(client.getUserName(), MessageType.ADD_ROOM, textField.getText()));
			}
		});

		this.textField.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					respondToInput();
				}
			}
		});

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				textField.requestFocus();
			}
		});

	}

	private void respondToInput() {
		String input = textField.getText();
		if (input.length() > 0) {
			textArea.append(input + "\n");
		}
		textField.setText("");
	}

	public void addClient(String name) {
		this.listModel.addElement(name);
	}

	public static void main(String[] args) {

		Object[] list = { "Kory", "Sara", "The street car" };

		GuiRoom room = new GuiRoom("Kory's Room", list, null, null);
		room.setDefaultCloseOperation(EXIT_ON_CLOSE);
		room.setVisible(true);

		room.addClient("Billy");

	}

}
