package hanwool.lotto.data.event

import hanwool.lotto.data.model.LottoDto
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class LottoDataEvent {
    private val bus: PublishSubject<LottoDto> = PublishSubject.create()

    public fun send(dto: LottoDto) {
        bus.onNext(dto)
    }

    public fun toObserverable(): Observable<LottoDto> {
        return bus
    }

    public fun hasObservers(): Boolean {
        return bus.hasObservers()
    }
}