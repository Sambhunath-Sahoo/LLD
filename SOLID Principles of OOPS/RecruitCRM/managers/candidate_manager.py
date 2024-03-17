from interfaces.candidate_management_interface import CandidateManagementInterface

class CandidateManager(CandidateManagementInterface):
    def __init__(self):
        self.candidates = {}

    def add_candidate(self, candidate):
        self.candidates[candidate['id']] = candidate

    def update_candidate(self, candidate):
        self.candidates[candidate['id']] = candidate

    def get_candidate(self, candidate_id):
        return self.candidates.get(candidate_id)
