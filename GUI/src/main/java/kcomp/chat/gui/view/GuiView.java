package kcomp.chat.gui.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import kcomp.chat.common.entities.Client;
import kcomp.chat.common.entities.SocketProperties;
import kcomp.chat.common.enums.MessageType;
import kcomp.chat.common.listeners.Listener;
import kcomp.chat.common.listeners.LobbyListener;
import kcomp.chat.common.listeners.UserClientListener;
import kcomp.chat.common.messages.Message;
import kcomp.chat.common.service.ClientService;

@Component
@Configurable
public class GuiView extends JFrame implements View {

	private static final long serialVersionUID = 1L;
	private JButton addRoom;
	private JButton test;
	private JPanel tablePanel;
	private JPanel buttonPanel;
	private JTable table;
	private DefaultTableModel tableModel;

	private Client client;

	@Autowired
	private ClientService clientService;

	@Autowired
	private SocketProperties props;

	public GuiView() {
		this.setTitle("Chat Lobby");
		this.setSize(600, 400);

		createPanels();

		this.createLobbyTable();
		createAddButton();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.test = new JButton("Test");

		this.test.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				client.send(props.getRoom() + "/roomCat",
						new Message<String>(client.getUserName(), MessageType.ADD_ROOM, "RoomName"));
			}
		});

		// this.buttonPanel.add(test);

	}

	private void createPanels() {

		tablePanel = new JPanel();
		buttonPanel = new JPanel();

		this.add(tablePanel, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.EAST);

	}

	private void createAddButton() {

		addRoom = new JButton("Add Room");
		this.buttonPanel.add(addRoom);
		addRoom.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				requestAddRoom();
			}
		});

	}

	private void requestAddRoom() {

		String roomName = JOptionPane.showInputDialog(this, "Room Name", "Adding Room", JOptionPane.OK_CANCEL_OPTION);

		if (!StringUtils.isEmpty(roomName)) {
			if (client != null) {
				client.send(props.getAddRoom(),
						new Message<Object>(client.getUserName(), MessageType.ADD_ROOM, roomName));
			} else {
				// TODO add exception
			}
		}
	}

	private void openRoomView(String name, Object[] clients) {
		GuiRoom room = new GuiRoom(name, clients, props, clientService.createClient(name));
		room.setVisible(true);
	}

	private void createLobbyTable() {

		tableModel = new DefaultTableModel();

		table = new JTable(tableModel);

		tableModel.addColumn("Name");
		tableModel.addColumn("Creator");

		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);

		this.table.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (e.getButton() == 1) {
					int row = table.rowAtPoint(e.getPoint());
					String roomName = (String) table.getModel().getValueAt(row, 0);
					client.send(props.getOpenRoom(),
							new Message<String>(client.getUserName(), MessageType.OPEN_ROOM, roomName));

				}

			};

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		this.tablePanel.add(scrollPane);

	}

	private void addRoomToLobby(String roomName, String creator) {
		System.out.println("Adding room to view");
		tableModel.addRow(new Object[] { roomName, creator });

	}

	@Override
	public void start() {

		this.setVisible(true);

		String name = null;

		while (name == null) {
			name = JOptionPane.showInputDialog(this, "Enter your username", "Login", JOptionPane.OK_CANCEL_OPTION);

			if (name == null || StringUtils.isEmpty(name)) {
				String result = JOptionPane.showInputDialog(this, "You must enter a username to continue", "Warning",
						JOptionPane.OK_CANCEL_OPTION);
				if (result == null) {
					System.exit(0);
				}
			}
		}

		System.out.println("Your name is " + name);

		login(name);

	}

	private void login(String name) {

		client = clientService.createClient(name);

		Listener<LobbyListener> listener = new UserClientListener();

		listener.setListener(new LobbyListener() {

			@Override
			public void addRoom(String roomName, String creator) {
				addRoomToLobby(roomName, creator);
			}

			@Override
			public void openRoom(String roomName, Object[] clients) {
				openRoomView(roomName, clients);
			}
		});

		client.subscribe(props.getTopics().get(0));

		client.setListener(listener);

		client.send(props.getLoginUrl(), new Message<>(name, MessageType.LOG_IN, null));
	}

}
