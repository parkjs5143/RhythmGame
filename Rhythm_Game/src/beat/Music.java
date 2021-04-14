package beat;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import javazoom.jl.player.Player;

public class Music extends Thread {	// 쓰레드 상속
	
	private Player player;	// 다운로드 받은 jl 라이브러리
	private boolean isLoop;	// 음악을 꺼지도록 설정
	private File file;
	private FileInputStream fis;
	private BufferedInputStream bis;
	
	public Music(String name, boolean isLoop) {	// 곡의 제목과 무한반복여부를 생성자로 받는다.
		try {	// 예외처리
			this.isLoop = isLoop;	// 변수 초기화
			file = new File(Main.class.getResource("../music/" + name).toURI());	
			// music 폴더 안에 있는 해당 이름의 파일을 실행시킨다는 의미. toURI 해당파일의 위치를 가져올 수 있도록 설정
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);	// 해당파일을 버퍼로 읽어올 수 있게 설정.
			player = new Player(bis);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public int getTime() {	// 현재실행되고 있는 음악이 현재 어떤 위치에서 실행되고 있는지 알려줌.
		if(player == null)
			return 0;
		return player.getPosition();
	}
	
	public void close() {	// 사용자가 해당 곡의 재생을 멈추고 싶을때 안정적으로 빠져나갈 수 있게
		isLoop = false;
		player.close();
		this.interrupt();	// interrupt : 해당 쓰레드를 중지상태로 만든다.
	}
	
	@Override
	public void run() {	// 쓰레드를 상속받으면 무조건 오버라이드
		try {
			do {	// 내부에 곡을 실행하는 코드 작성 (위에코드 복사)
				player.play();
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				player = new Player(bis);
			} while (isLoop);	// 반복문으로 true 값이면 무한반복하도록
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
}
