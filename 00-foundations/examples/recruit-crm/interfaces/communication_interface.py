from abc import ABC, abstractmethod


class CommunicationInterface(ABC):
    @abstractmethod
    def send_email(self, candidate, message):
        pass

    @abstractmethod
    def send_sms(self, candidate, message):
        pass
