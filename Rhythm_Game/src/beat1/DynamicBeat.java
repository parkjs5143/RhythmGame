package beat1;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class DynamicBeat extends JFrame {	// Jframe 라이브러리를 사용해준다.
	
	private Image screenImage;	
	private Graphics screenGraphic;
	// 더블 버퍼링을 위해 이미지를 화면에 담는 인스턴스
	
	private Image introbackground;
	// 이미지 파일을 담을 수 있는 객체 생성
	
	public DynamicBeat() {
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
		
		introbackground = new ImageIcon(Main.class.getResource("../images/introbackground.jpg")).getImage();
		// main클래스 위치의 기반의 이미지 인스턴스를 변수에 초기화
		
		Music introMusic = new Music("introMusic.mp3", true);
		// 시작화면에서 음악이 무한반복하게 해줌. true값을 넣어줘서 직접종료시키기 전에는 계속 반복하게된다.
		introMusic.start();
		// 게임의 실행과 동시에 게임이 종료
	}
	
	public void paint(Graphics g) {	// 프로그램이 실행되는 순간 가장 첫번째로 화면에 그려지는 약속된 메서드
		screenImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);	// 크기만큼의 이미지를 만들고 변수에 넣어준다.
		screenGraphic = screenImage.getGraphics();	// 그래픽객체를 가져옴.
		screenDraw(screenGraphic);	
		g.drawImage(screenImage, 0, 0, null);	// 스크린 이미지가 화면 창에 그려짐
	}
	
	public void screenDraw(Graphics g) {
		g.drawImage(introbackground, 0, 0, null);  // 전체이미지에 그려질 수 있도록한다.
		this.repaint();	
		// 페인트함수를 다시 불러와 다시 화면에 그려줌. 프로그램이 실행되는 매 순간마다 프로그램이 종료되는 순간까지 반복됨.
		
	}
}
