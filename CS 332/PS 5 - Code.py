# Universal DFA
# Author: Mark Bun and Huiwen He
# Boston University CS 332
# March 4, 2021

# This file contains starter code to help you design a program that 
# 1) Prompts the user for a string encoding a DFA D and a binary string w
# 2) Prints the sequence of states D enters when run on w, as well as whether D accepts or rejects on input w
#
# Your job is to implement the function simulate_DFA that simulates running D on w and outputs the list of states it enters

import re

class UniversalDFA(object):
   
    # Parse an encoded DFA into a 3-tuple
    def encoding_to_triple(self, encoded_DFA):
        list = encoded_DFA.split("#")
        raw_transitions = list[0]
        
        # Use regular expression(!) to split string encoding transition function into list of triples [(p, a, q), ...]
        list_transitions = re.findall(r'(\w+,\w+,\w+)', raw_transitions)
        
        # Convert list of transitions into dictionary
        transitions = {}    # initialize empty dictionary
        for triplet in list_transitions:
            trip_list = triplet.split(",")
            transitions[(trip_list[0], trip_list[1])] = trip_list[2]
            
        start_state = list[1]
        final_states = list[2].split(",")
        return (transitions, start_state, final_states)

######################################################
######## DON'T TOUCH THE CODE ABOVE THIS LINE ########
######################################################
    
    # Input: 
    # -----------------
    # A string encoded_DFA encoding a DFA over the alphabet {0, 1}, and a string input_string over alphabet {0, 1}
    # The format of the encoding for a DFA is "d#q0#F" where d is a list of triples capturing the transition function, q0 is the start state, and F is a comma-separated list of accept states
    # Example: encoded_DFA = "(q0,0,q1),(q0,1,q0),(q1,0,q0),(q1,1,q1)#q0#q1" encodes a DFA testing whether its input contains an odd number of 0's
    # The transition function d is d(q0, 0) = q1, d(q0, 1) = q0, d(q1, 0) = q0, d(q1, 1) = q1
    # input_string = "00101111" specifies a binary input to the above DFA
    # DFA = (q0,0,q1),(q0,1,q0),(q1,0,q1),(q1,1,q2),(q2,0,q3),(q2,1,q2),(q3,0,q3),(q1,1,q0)#q0#q0,q1
    # x = 0011110
    # Output:
    # ----------------
    # Return a list of strings consisting of the states the DFA enters upon reading input_string, followed by whether the DFA accepts or rejects
    # Example: transcript = ["q0", "q1", "q0", "q0", "q1", "q1", "q1", "q1", "q1", "accept"]
    def simulate_DFA(self, encoded_DFA, input_string):
        (transitions, start_state, final_states) = self.encoding_to_triple(encoded_DFA)
        transcript = []
        currentState = start_state
        transcript.append(currentState)
        
        
        for symbol in input_string:
            currentState = transitions[(currentState, symbol)]
            transcript.append(currentState)
        
        finalString = "reject"
        if (currentState in final_states):
            finalString = "accept"
        
        transcript.append(finalString)
        
        return transcript


######################################################
######## DON'T TOUCH THE CODE BELOW THIS LINE ########
######################################################

    def prompt(self):
        # Prompts the user for an encoded DFA and a string, and simulates the DFA on that string
        encoded_DFA = input("Enter your DFA: ")  
        input_string = input("Enter your string: ")  
        transcript = self.simulate_DFA(encoded_DFA, input_string)
        for str in transcript:
            print(str)


if __name__ == "__main__":
    universal_DFA = UniversalDFA()
    universal_DFA.prompt()




