class FiniteAutomata:

    def __init__(self, Q, E, q0, F, S):
        self.Q = Q
        self.E = E
        self.q0 = q0
        self.F = F
        self.S = S

    @staticmethod
    def getLine(line):
        return line.strip().split(' ')[2:]

    @staticmethod
    def validate(Q, E, q0, F, S):
        if q0 not in Q:
            return False
        for state in F:
            if state not in Q:
                return False
        for key in S.keys():
            if key[0] not in Q:
                return False
            if key[1] not in E:
                return False
            for destination in S[key]:
                if destination not in Q:
                    return False
        return True

    @staticmethod
    def readFromFile(filename):
        with open(filename) as file:
            Q = FiniteAutomata.getLine(file.readline())
            E = FiniteAutomata.getLine(file.readline())
            q0 = FiniteAutomata.getLine(file.readline())[0]
            F = FiniteAutomata.getLine(file.readline())

            file.readline()
            S = {}
            for line in file:
                source = line.strip().split('->')[0].strip().replace('(', '').replace(')', '').split(',')[0]
                route = line.strip().split('->')[0].strip().replace('(', '').replace(')', '').split(',')[1]
                destination = line.strip().split('->')[1].strip()

                if (source, route) in S.keys():
                    S[(source, route)].append(destination)
                else:
                    S[(source, route)] = [destination]

            if not FiniteAutomata.validate(Q, E, q0, F, S):
                raise Exception("Invalid finite automata!")

            return FiniteAutomata(Q, E, q0, F, S)

    def isDfa(self):
        for k in self.S.keys():
            if len(self.S[k]) > 1:
                return False
        return True

    def isAccepted(self, sequence):
        if self.isDfa():
            currentState = self.q0
            for symbol in sequence:
                if (currentState, symbol) in self.S.keys():
                    currentState = self.S[(currentState, symbol)][0]
                else:
                    return False
            return currentState in self.F
        return False

    def __str__(self):
        return "Q = { " + ', '.join(self.Q) + " }\n" \
                "E = { " + ', '.join(self.E) + " }\n" \
                "q0 = { " + self.q0 + " }\n" \
                "F = { " + ', '.join(self.F) + " }\n" \
                "S = " + str(self.S)

