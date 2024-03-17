from abc import ABC, abstractmethod

class CandidateManagementInterface(ABC):
    @abstractmethod
    def add_candidate(self, candidate):
        pass

    @abstractmethod
    def update_candidate(self, candidate):
        pass

    @abstractmethod
    def get_candidate(self, candidate_id):
        pass
