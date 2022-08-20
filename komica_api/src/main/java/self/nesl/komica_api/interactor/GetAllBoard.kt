package self.nesl.komica_api.interactor

import self.nesl.komica_api.model.boards

class GetAllBoard {
    fun invoke() = boards()
}