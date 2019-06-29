package racing.domain;

import racing.domain.accelerator.RandomAccelerator;
import racing.view.RacingMonitorView;
import racing.vo.PlayerRecord;
import racing.vo.RacingReport;
import racing.vo.StageOption;

import java.util.List;

/**
 * 옵션을 받아 게임(컨트롤러)을 생성하고 뷰를 주입받아 제어하는 컨트롤러
 */
public class GameStageController {

	private Stage stage;

	private RacingMonitorView view;

	public GameStageController(StageOption option, RacingMonitorView view){
		Stage.StageBuilder builder = Stage.builder(option.getEntrySize(), option.getRoundLimit());

		for(int i = 0; i < option.getEntrySize(); i++) {
			builder.addToEntry(option.getPlayerName(i), new RacingCar(new RandomAccelerator()));
		}

		this.stage = builder.build();
		this.view = view;
	}

	public void play() {
	    this.view.renderStart("실행 결과");

		RacingReport report = null;

		do{
			report = this.stage.play();
            this.view.renderRound(report.getRecords());
		}while (report.hasNext());

        this.view.renderFinish(report.getWinners());

	}
}