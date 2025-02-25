package lotto.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WinnerCalculatorTest {
    private static final int EXPECTED_FIRST_PRIZE_WINNERS = 1;
    private static final int EXPECTED_SECOND_PRIZE_WINNERS = 1;
    private static final int EXPECTED_THIRD_PRIZE_WINNERS = 1;
    private static final int EXPECTED_FOURTH_PRIZE_WINNERS = 1;
    private static final int EXPECTED_FIFTH_PRIZE_WINNERS = 1;
    private static final int EXPECTED_NO_PRIZE_WINNERS = 1;
    private static final int BONUS_NUMBER = 7;
    private static final int INITIAL_COUNT = 0;

    private Lotto winningLotto;
    private BonusNumber bonusNumber;
    private WinningCalculator winningCalculator;

    @BeforeEach
    void setUp() {

        LottoNumberGenerator lottoNumberGenerator = new LottoNumberGenerator() {
            @Override
            public List<Lotto> getLottoNumbers() {
                return Arrays.asList(
                        new Lotto(Arrays.asList(1, 2, 3, 4, 5, 6)),
                        new Lotto(Arrays.asList(1, 2, 3, 4, 5, 7)),
                        new Lotto(Arrays.asList(1, 2, 3, 4, 5, 8)),
                        new Lotto(Arrays.asList(1, 2, 3, 4, 9, 10)),
                        new Lotto(Arrays.asList(1, 2, 3, 11, 12, 13)),
                        new Lotto(Arrays.asList(1, 2, 14, 15, 16, 17))
                );
            }
        };
        Lotto winningLotto = new Lotto(Arrays.asList(1, 2, 3, 4, 5, 6));
        BonusNumber bonusNumber = new BonusNumber() {
            @Override
            public int getBonus() {
                return BONUS_NUMBER;
            }
        };
        winningCalculator = new WinningCalculator(lottoNumberGenerator, winningLotto, bonusNumber);
    }

    @DisplayName("당첨자 계산을 올바르게 수행하는지 테스트")
    @Test
    void testWinnerCalculation() {
        LinkedHashMap<LottoRank, Integer> result = winningCalculator.calculate();

        assertThat(result.get(LottoRank.FIRST)).isEqualTo(EXPECTED_FIRST_PRIZE_WINNERS);
        assertThat(result.get(LottoRank.SECOND)).isEqualTo(EXPECTED_SECOND_PRIZE_WINNERS);
        assertThat(result.get(LottoRank.THIRD)).isEqualTo(EXPECTED_THIRD_PRIZE_WINNERS);
        assertThat(result.get(LottoRank.FOURTH)).isEqualTo(EXPECTED_FOURTH_PRIZE_WINNERS);
        assertThat(result.get(LottoRank.FIFTH)).isEqualTo(EXPECTED_FIFTH_PRIZE_WINNERS);
        assertThat(result.get(LottoRank.MISS)).isEqualTo(EXPECTED_NO_PRIZE_WINNERS);
    }

    @DisplayName("초기 당첨자 수가 모두 0으로 설정되어 있는지 테스트")
    @Test
    void testInitialWinnerCount() {
        LinkedHashMap<LottoRank, Integer> initNumber = winningCalculator.getWinnerCount();

        assertThat(initNumber.values()).allMatch(number -> number == INITIAL_COUNT);
    }
}