package beat;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class DynamicBeat extends JFrame { // JFrame 라이브러리를 사용해준다.

	private Image screenImage;
	private Graphics screenGraphic;
	// 더블 버퍼링을 위해 이미지를 화면에 담는 인스턴스

	private ImageIcon exitButtonBasicImage = new ImageIcon(Main.class.getResource("../images/exitButtonBasic.png"));
	// 버튼의 이미지 아이콘 생성(기본이미지 아이콘)
	private ImageIcon exitButtonEnterImage = new ImageIcon(Main.class.getResource("../images/exitButtonEntered.png"));
	// 버튼의 이미지 아이콘 생성(Entered 아이콘)
	private Image introbackground = new ImageIcon(Main.class.getResource("../images/introbackground.jpg")).getImage();
	// 이미지 파일을 담을 수 있는 객체 생성
	
	private JLabel menuBar = new JLabel(new ImageIcon(Main.class.getResource("../images/menuBar.png")));
	// 메뉴바 객체 생성, 메뉴바 이미지 담아줌.
	
	private JButton exitButton = new JButton(exitButtonBasicImage);	
	// 버튼 객체 생성, 버튼 이미지 담아줌.

	private int mouseX, mouseY;	// 메뉴바를 드래그 할 때 위치가 변경되도록 설정하기위해 마우스 좌표 변수 설정
	
	public DynamicBeat() {
		setUndecorated(true);
		// 실행했을 때 기본적으로 존재하는 메뉴바가 사라짐.
		setTitle("DynamicBeat");
		// 타이틀 설정
		setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		// main에서 설정해준 가로세로 설정
		setResizable(false);
		// 한번 만들어진 게임창을 사용자가 줄이거나 늘릴수 없게함.
		setLocationRelativeTo(null);
		// 게임창이 컴퓨터의 정중장에 위치
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 게임창을 종료했을 때 프로그램 전체가 종료됨.
		setVisible(true);
		// 눈에 게임창이 보이게 함.
		setBackground(new Color(0, 0, 0, 0));	//paintComponents 했을 때 배경이 하얀색으로 바뀜.
		setLayout(null);	// Button이나 JLabel을 넣었을 때 위치 그대로 설정되게함.
		
		
		exitButton.setBounds(1245, 0, 30, 30);	// 버튼 위치 설정
		exitButton.setBorderPainted(false);		// 버튼 테두리 색상 없게 설정
		exitButton.setContentAreaFilled(false);	// 버튼 공간 색상 설정
		exitButton.setFocusPainted(false);		// 버튼에 포커스가 맞춰졌을 때 색상 설정
		exitButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {	// 마우스가 버튼위에 올라갔을 때
				exitButton.setIcon(exitButtonEnterImage);	// 해당 Enter이미지로 설정
				exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));	// 손가락모양 마우스로 설정
				Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3",false);	//한번만 음악이 실행되도록
				buttonEnteredMusic.start();
			}
			public void mouseExited(MouseEvent e) {	// 마우스가 다시 나왔을 때
				exitButton.setIcon(exitButtonBasicImage);	// 다시 Basic이미지로 설정
				exitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));	// 기본 마우스
			}
			public void mousePressed(MouseEvent e) {	// 마우스를 눌렀을 경우
				Music buttonPressedMusic = new Music("buttonPressedMusic.mp3",false);
				buttonPressedMusic.start();
				try {
					Thread.sleep(500);	// 버튼을 누르자마자 종료되면 음악X 일정시간 지난후 종료되도록 설정
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				System.exit(0);	// 게임종료
			}
		});
		add(exitButton);

		menuBar.setBounds(0, 0, 1280, 30);	// 메뉴바의 위치 설정
		menuBar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) { // 마우스 이벤트가 발생했을 때 설정
				mouseX = e.getX();
				mouseY = e.getY();
			}
		});
		menuBar.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {	// 드래그 이벤트 발생했을때 설정
				int x = e.getXOnScreen();	// 스크린의 x좌표를 가져오고
				int y = e.getYOnScreen();	// 스크린의 y좌표를 가져온다.
				setLocation(x - mouseX, y - mouseY);	// JFrame의 위치 자체를 x와 y로 바꿔준다.
			}
		});	// 이렇게 하면 드래그 할 때마다 순간순간의 좌표값을 가져와서 창의 위치를 바꿔준다.
		add(menuBar);	// JFrame에 메뉴바가 추가됨.

		Music introMusic = new Music("introMusic.mp3", true);
		// 시작화면에서 음악이 무한반복하게 해줌. true값을 넣어줘서 직접종료시키기 전에는 계속 반복하게된다.
		introMusic.start();
		// 게임의 실행과 동시에 게임이 종료
	}

	public void paint(Graphics g) { // 프로그램이 실행되는 순간 가장 첫번째로 화면에 그려지는 약속된 메서드
		screenImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT); // 크기만큼의 이미지를 만들고 변수에 넣어준다.
		screenGraphic = screenImage.getGraphics(); // 그래픽객체를 가져옴.
		screenDraw(screenGraphic);
		g.drawImage(screenImage, 0, 0, null); // 스크린 이미지가 화면 창에 그려짐
	}

	public void screenDraw(Graphics g) {
		g.drawImage(introbackground, 0, 0, null); // 전체이미지에 그려질 수 있도록한다.
		paintComponents(g); // 설정해준 이미지 이외의 그림을 그릴 수 있게 해줌. 항상존재하고 역동적이지 않기 때문에 이걸로 지정해준다.
		this.repaint();
		// 페인트함수를 다시 불러와 다시 화면에 그려줌. 프로그램이 실행되는 매 순간마다 프로그램이 종료되는 순간까지 반복됨.

	}
}
