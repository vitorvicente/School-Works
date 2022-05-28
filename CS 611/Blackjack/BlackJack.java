import java.util.ArrayList;
import java.util.Scanner;

public class BlackJack extends Game {
	public static final int STARTER_AMOUNT = 25000;
	public static final int MINIMUM_BET = 10;

	private CardPlayer dealer;
	private CardPlayer player;
	private Deck deck;

	private ArrayList<Integer> currentBets;
	private boolean hasSplit;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public BlackJack() {
		super("BlackJack", false);
	}

	public BlackJack(String gameName) {
		super(gameName, false);
		this.setMessages();

		this.printer("welcome");

		this.entitiesSetup();

		this.play();
	}

	/* ============ */
	/* Game Methods */
	/* ============ */

	/*
	 * Method to set up both players, and decide which is the dealer and which is
	 * the player
	 */
	@Override
	protected void entitiesSetup() {
		CardPlayer p1 = new CardPlayer(1);
		CardPlayer p2 = new CardPlayer(2);

		boolean p1Dealer = false;

		try {
			p1Dealer = this.getYesNo(p1.getName() + " would you like to be the dealer for this game?");
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (p1Dealer) {
			this.setDealer(p1);
			this.setPlayer(p2);
		} else {
			this.setDealer(p2);
			this.setPlayer(p1);
		}

	}

	/*
	 * Main play method, prints instructions, and runs "games" always prompting each
	 * player to play again after the game is done
	 */
	@Override
	protected void play() {
		this.printer("instructions");

		while (true) {
			this.innerGame();

			boolean again = false;
			try {
				again = this.getYesNo("Would you like to play again?");
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (!again)
				break;
			else {
				this.askReplace();
			}
		}

		this.printer("goodbye");
	}

	/*
	 * Inner game method, is responsible for running "rounds", and keeping track of
	 * the balances, also resets hands and deck after every round.
	 * 
	 * Further it does some setup at the start of each round, dealing cards and
	 * making the minimum bet
	 */
	private void innerGame() {
		this.getDealer().setBalance(-1);
		this.getPlayer().setBalance(BlackJack.STARTER_AMOUNT);
		this.resetGame();

		// Round
		while (this.getPlayer().getBalance() > BlackJack.MINIMUM_BET) {
			this.player.getHand().addCard(this.deck.dealCard());
			this.player.getHand().addCard(this.deck.dealCard());
			this.dealer.getHand().addCard(this.deck.dealCard());
			this.dealer.getHand().addCard(this.deck.dealCard());

			this.hasSplit = false;
			this.currentBets = new ArrayList<Integer>();

			this.currentBets.add(BlackJack.MINIMUM_BET);
			this.getPlayer().removeBalance(BlackJack.MINIMUM_BET);

			this.playTurns();

			this.resetGame();
		}
	}

	/*
	 * This is the key method, it plays the turns in each round, it starts by
	 * printing each hands, and the player balance.
	 * 
	 * It asks the player if they want to raise their bet, and asks them for next
	 * move, then depending on the move, it goes onto each individual move method,
	 * and then it works with whatever returns from these methods. These details are
	 * described below.
	 */
	private void playTurns() {
		String nextMove = "";
		boolean continueTurns = true;
		String checker = "";

		while (continueTurns) {
			System.out.println();
			this.getDealer().printHand(true);
			this.getPlayer().printHand(false);
			System.out.println("Current Player Balance: " + this.getPlayer().getBalance());
			System.out.println();

			System.out.println("Playing with hand: " + this.getPlayer().getHand());

			this.currentBets.set(0, this.currentBets.get(0) + this.getIncreasedBet());

			nextMove = this.getNextMove(this.getPlayer().getHand(), this.currentBets.get(0));

			if (nextMove.equals("split"))
				checker = this.split();
			else if (nextMove.equals("hit"))
				checker = this.hit(0);
			else if (nextMove.equals("double"))
				checker = this.doubleBet(0);
			else if (nextMove.equals("stand"))
				checker = this.stand(0);

			/*
			 * From the above methods, we get one of the following returns:
			 * 
			 * - Bust - Player's hand in question is bust
			 * 
			 * - BlackJack - Player's hand in question is a BlackJack
			 * 
			 * - Double BlackJack - This is specific to split, when both hands can get
			 * BlackJack at once
			 * 
			 * - BlackJackTwo - This is specific to split, when the secondary hand can get
			 * BlackJack
			 * 
			 * - Won - Player won with current hand
			 * 
			 * - Lost - Player lost with current hand
			 * 
			 * - DealerBust - Dealer went bust
			 * 
			 * - Nothing - Nothing special happened
			 */

			/*
			 * This main if is here to deal with the possibility of there being two hands
			 * because of the split, so the scenarios are slightly different.
			 */
			if (this.getPlayer().getHandTwo().getHand().size() > 0) {

				/*
				 * If the player won with the first hand, the second hand is forced to win/loose
				 */
				if (checker.equals("Won")) {
					System.out.println(
							this.getPlayer().getName() + " has won with his first hand: " + this.getPlayer().getHand());
					System.out.println("As per house rules, we are now forcing a stand for his second hand: "
							+ this.getPlayer().getHandTwo());
					this.getPlayer().addBalance(this.currentBets.get(0) * 2);
					this.currentBets.set(0, 0);
					checker = this.stand(1);

					if (checker.equals("Won")) {
						System.out.println(this.getPlayer().getName() + " has won with his second hand: "
								+ this.getPlayer().getHandTwo());
						this.getPlayer().addBalance(this.currentBets.get(1) * 2);
						this.currentBets.set(1, 0);
						continueTurns = false;
					} else {
						System.out.println(this.getPlayer().getName() + " has lost with his second hand: "
								+ this.getPlayer().getHandTwo());
						this.currentBets.set(1, 0);
						continueTurns = false;
					}

					System.out.println("Dealer's hand: " + this.getDealer().getHand());

					/*
					 * If the player BlackJacked or went Bust with their first hand, their second
					 * becomes the first, and points are attributed if needed
					 */
				} else if (checker.equals("BlackJack") || checker.equals("Bust")) {
					if (checker.equals("BlackJack")) {
						System.out.println(this.getPlayer().getName() + " has gotten BlackJaack with his first hand: "
								+ this.getPlayer().getHand());
						this.getPlayer().addBalance(this.currentBets.get(0) * 2);
					} else {
						System.out.println(this.getPlayer().getName() + " has gone Bust with his first hand: "
								+ this.getPlayer().getHand());
					}

					System.out.println("As per house rules, the second hand now becomes the first!");

					this.getPlayer().setHand(this.getPlayer().getHandTwo());
					this.getPlayer().setHandTwo(new CardHand());
					this.currentBets.set(0, this.currentBets.get(1));
					this.currentBets.remove(1);

					/*
					 * If the player lost, then their second hand is also forced to win or loose,
					 * and the method reacts as such
					 */
				} else if (checker.equals("Lost")) {
					System.out.println(this.getPlayer().getName() + " has lost with his first hand: "
							+ this.getPlayer().getHand());

					System.out.println("As per house rules, the second hand will now be forced to stand!");

					this.getPlayer().setHand(this.getPlayer().getHandTwo());
					this.getPlayer().setHandTwo(new CardHand());
					this.currentBets.set(0, this.currentBets.get(1));
					this.currentBets.remove(1);

					String checkerTwo = this.stand(0);

					if (checkerTwo.equals("Won")) {
						System.out.println(this.getPlayer().getName() + " has won with his second hand: "
								+ this.getPlayer().getHandTwo());
						this.getPlayer().addBalance(this.currentBets.get(0) * 2);
						this.currentBets.set(0, 0);
						continueTurns = false;
					} else {
						System.out.println(this.getPlayer().getName() + " has lost with his second hand: "
								+ this.getPlayer().getHandTwo());
						this.currentBets.set(0, 0);
						continueTurns = false;
					}

					System.out.println("Dealer's hand: " + this.getDealer().getHand());

					/*
					 * This is a unique scenario that comes of the Split method, where splitting the
					 * first hand can result in the second hand getting a BlackJack, if that
					 * happens, then the player gets the points and the second hand is deleted
					 */
				} else if (checker.equals("BlackJackTwo")) {
					System.out.println(this.getPlayer().getName() + " has gotten BlackJack with his second hand: "
							+ this.getPlayer().getHandTwo());
					this.getPlayer().addBalance(this.currentBets.get(1) * 2);

					System.out.println("As per house rules, this hand is no longer at play!");

					this.getPlayer().setHandTwo(new CardHand());
					this.currentBets.remove(1);

					/*
					 * Again this is a unique scenario, where after splitting the player gets both
					 * hands with BlackJack, if it happens the player gets the money and the round
					 * is over
					 */
				} else if (checker.equals("DoubleBlackJack")) {
					System.out.println(this.getPlayer().getName() + " has gotten BlackJaack with both hands!");
					System.out.println(this.getPlayer().getName() + " has won!");

					System.out.println("Dealer's hand: " + this.getDealer().getHand());

					this.getPlayer().addBalance(this.currentBets.get(0) * 2);
					this.getPlayer().addBalance(this.currentBets.get(1) * 2);
					this.currentBets.set(0, 0);
					this.currentBets.remove(1);
					this.getPlayer().setHand(new CardHand());
					this.getPlayer().setHandTwo(new CardHand());

					continueTurns = false;

					/*
					 * This method deals with the scenario that sees the dealer going bust after a
					 * stand, it gets the player to win for both hands
					 */
				} else if (checker.equals("DealerBust")) {
					System.out.println("The Dealer has gone bust with his hand: " + this.getDealer().getHand());
					System.out.println(this.getPlayer().getName() + " has won!");

					System.out.println("Dealer's hand: " + this.getDealer().getHand());

					this.getPlayer().addBalance(this.currentBets.get(0) * 2 + this.currentBets.get(1) * 2);
					this.getPlayer().setHand(new CardHand());
					this.getPlayer().setHandTwo(new CardHand());
					this.currentBets.remove(0);
					this.currentBets.remove(1);

					continueTurns = false;

					/*
					 * If absolutely nothing happens of the possibilities before, it prompts the
					 * player for the move for their second hands, and the checks are pretty much
					 * the same as above, so no more spammy comments
					 */
				} else if (checker.equals("Nothing")) {
					System.out.println("Playing with hand: " + this.getPlayer().getHandTwo());

					this.currentBets.set(1, this.currentBets.get(1) + this.getIncreasedBet());

					String nextMoveTwo = this.getNextMove(this.getPlayer().getHandTwo(), this.currentBets.get(1));
					String checkerTwo = "";

					if (nextMoveTwo.equals("hit"))
						checkerTwo = this.hit(1);
					else if (nextMoveTwo.equals("double"))
						checkerTwo = this.doubleBet(1);
					else if (nextMoveTwo.equals("stand"))
						checkerTwo = this.stand(1);

					// Player won with hand 2
					if (checkerTwo.equals("Won")) {
						System.out.println(this.getPlayer().getName() + " has won with his second hand: "
								+ this.getPlayer().getHandTwo());
						System.out.println("As per house rules, we are now forcing a stand for his first hand: "
								+ this.getPlayer().getHandTwo());

						this.getPlayer().addBalance(this.currentBets.get(1) * 2);
						this.currentBets.set(1, 0);
						checkerTwo = this.stand(0);

						if (checkerTwo.equals("Won")) {
							System.out.println(this.getPlayer().getName() + " has won with his first hand: "
									+ this.getPlayer().getHandTwo());
							this.getPlayer().addBalance(this.currentBets.get(0) * 2);
							this.currentBets.set(0, 0);
							continueTurns = false;
						} else {
							System.out.println(this.getPlayer().getName() + " has lost with his first hand: "
									+ this.getPlayer().getHandTwo());
							this.currentBets.set(0, 0);
							continueTurns = false;
						}

						System.out.println("Dealer's hand: " + this.getDealer().getHand());

						// If BlackJacked or busted with hand two
					} else if (checkerTwo.equals("BlackJack") || checker.equals("Bust")) {
						if (checkerTwo.equals("BlackJack")) {
							System.out.println(this.getPlayer().getName()
									+ " has gotten BlackJaack with his second hand: " + this.getPlayer().getHand());
							this.getPlayer().addBalance(this.currentBets.get(1) * 2);
						} else {
							System.out.println(this.getPlayer().getName() + " has gone Bust with his second hand: "
									+ this.getPlayer().getHand());
						}

						System.out.println("As per house rules, this hand will no longer be at play!");

						this.getPlayer().setHandTwo(new CardHand());
						this.currentBets.remove(1);

						// Player lost with hand two
					} else if (checkerTwo.equals("Lost")) {
						System.out.println(this.getPlayer().getName() + " has lost with his second hand: "
								+ this.getPlayer().getHand());

						System.out.println("As per house rules, his first hand will be forced to stand!");

						this.getPlayer().setHandTwo(new CardHand());
						this.currentBets.remove(1);

						checkerTwo = this.stand(1);

						if (checkerTwo.equals("Won")) {
							System.out.println(this.getPlayer().getName() + " has won with his first hand: "
									+ this.getPlayer().getHandTwo());
							this.getPlayer().addBalance(this.currentBets.get(0) * 2);
							this.currentBets.set(0, 0);
							continueTurns = false;
						} else {
							System.out.println(this.getPlayer().getName() + " has lost with his first hand: "
									+ this.getPlayer().getHandTwo());
							this.currentBets.set(0, 0);
							continueTurns = false;
						}

						System.out.println("Dealer's hand: " + this.getDealer().getHand());

						// Dealer went bust
					} else if (checkerTwo.equals("DealerBust")) {
						System.out.println("The Dealer has gone bust with his hand: " + this.getDealer().getHand());
						System.out.println(this.getPlayer().getName() + " has won!");

						System.out.println("Dealer's hand: " + this.getDealer().getHand());

						this.getPlayer().addBalance(this.currentBets.get(0) * 2 + this.currentBets.get(1) * 2);
						this.getPlayer().setHand(new CardHand());
						this.getPlayer().setHandTwo(new CardHand());
						this.currentBets.remove(1);
						this.currentBets.remove(0);
						continueTurns = false;
					}

					/*
					 * If nothing happens then the game moves on, and another turn begins.
					 */
				}

				/*
				 * Below are the same methods, except for a player with a single hands, this
				 * changes some things, including when/how the game reacts to each response
				 */
			} else {

				// BlackJack or Won for player
				if (checker.equals("BlackJack") || checker.equals("Won")) {
					System.out.println(
							this.getPlayer().getName() + " has won with his first hand: " + this.getPlayer().getHand());

					System.out.println("Dealer's hand: " + this.getDealer().getHand());

					this.getPlayer().addBalance(this.currentBets.get(0) * 2);
					this.currentBets.set(0, 0);
					continueTurns = false;

					// Dealer went bust
				} else if (checker.equals("DealerBust")) {
					System.out.println("The Dealer has gone bust with his hand: " + this.getDealer().getHand());
					System.out.println(this.getPlayer().getName() + " has won!");

					System.out.println("Dealer's hand: " + this.getDealer().getHand());

					this.getPlayer().addBalance(this.currentBets.get(0) * 2);
					this.getPlayer().setHand(new CardHand());
					this.currentBets.remove(0);
					continueTurns = false;

					// Player went Bust
				} else if (checker.equals("Bust")) {
					System.out.println(this.getPlayer().getName() + " has gone Bust with his first hand: "
							+ this.getPlayer().getHand());

					System.out.println("Dealer's hand: " + this.getDealer().getHand());

					this.currentBets.remove(0);
					this.getPlayer().setHand(new CardHand());
					continueTurns = false;

					// Player lost
				} else if (checker.equals("Lost")) {
					System.out.println(this.getPlayer().getName() + " has lost with his first hand: "
							+ this.getPlayer().getHand());

					System.out.println("Dealer's hand: " + this.getDealer().getHand());

					this.currentBets.remove(0);
					this.getPlayer().setHand(new CardHand());
					continueTurns = false;
				}
			}
		}

		System.out.println();
		System.out.println("Round over! Restarting");
		System.out.println();
	}

	private String split() {
		/*
		 * Make the split, by getting the second card and putting it into a new hand and
		 * apply this hand to the player. Then draw two new cards and give one to each
		 * hand. Proceed to remove the player balance and applying that bet to the new
		 * hand.
		 */
		Card newCardOne = this.deck.dealCard();
		Card newCardTwo = this.deck.dealCard();
		Card transferCard = this.getPlayer().getHand().getHand().get(1);

		this.getPlayer().getHand().getHand().remove(1);
		CardHand newHand = new CardHand();
		newHand.addCard(transferCard);
		newHand.addCard(newCardTwo);
		this.getPlayer().getHand().addCard(newCardOne);
		this.getPlayer().setHandTwo(newHand);

		this.getPlayer().removeBalance(this.currentBets.get(0));
		this.currentBets.add(this.currentBets.get(0));

		/*
		 * Now I need to check for BlackJack for both decks and return accordingly
		 */

		boolean BlackJackOne = this.checkValueHand(this.getPlayer().getHand(), null) == 21;
		boolean BlackJackTwo = this.checkValueHand(this.getPlayer().getHandTwo(), null) == 21;

		if (BlackJackOne && BlackJackTwo)
			return "DoubleBlackJack";
		else if (BlackJackOne)
			return "BlackJack";
		else if (BlackJackTwo)
			return "BlackJackTwo";
		else
			return "Nothing";
	}

	/*
	 * This method deals with the hit command for either of the hands, giving them a
	 * card, and checking for Bust, or BlackJack, everything else is not needed to
	 * be tested here
	 */
	private String hit(int handIndex) {
		Card newCard = this.deck.dealCard();
		int val = 0;

		if (handIndex == 0) {
			this.getPlayer().getHand().addCard(newCard);
			val = this.checkValueHand(this.getPlayer().getHand(), null);
		} else {
			this.getPlayer().getHandTwo().addCard(newCard);
			val = this.checkValueHand(this.getPlayer().getHandTwo(), null);
		}

		if (val > 21)
			return "Bust";
		else if (val == 21)
			return "BlackJack";
		else
			return "Nothing";
	}

	/*
	 * Doublebet deals with well, the double bet command, it quite literally doubles
	 * the bet on the hand in question, it then proceeds to hit, check if the hit
	 * process returned nothing, if it doesn't, it means the hit busted or
	 * blackjacked, so no need to stand.
	 * 
	 * If it didn't do any of this, then as per the rules, the player must stand, so
	 * it calls the stand method, and returns whatever it gets
	 */
	private String doubleBet(int handIndex) {
		this.getPlayer().removeBalance(this.currentBets.get(handIndex));
		this.currentBets.set(handIndex, this.currentBets.get(handIndex));

		String hitReturn = this.hit(handIndex);

		if (hitReturn.equals("Nothing"))
			return this.stand(handIndex);
		else
			return hitReturn;
	}

	/*
	 * The stand method aims to get the value of the current hand in question, check
	 * and return in case of Bust or BlackJack (even though theoretically this
	 * should never occur at this point), and otherwise check and return whether the
	 * player won or lost
	 */
	private String stand(int handIndex) {
		int val = 0;
		CardHand ch = null;

		if (handIndex == 0)
			ch = this.getPlayer().getHand();
		else
			ch = this.getPlayer().getHandTwo();

		val = this.checkValueHand(ch, null);

		if (val > 21)
			return "Bust";
		else if (val == 21)
			return "BlackJack";
		else {
			if (this.getDealerDraw() > 21)
				return "DealerBust";
			else {
				if (this.checkWinner(ch))
					return "Won";
				else
					return "Lost";
			}
		}
	}

	/*
	 * This method checks and returns whether the player has won, checking for the
	 * values of both Dealer and Player, comparing them, and returning true if the
	 * player won, or false if otherwise
	 * 
	 * It also checks for special scenarios such as the special BlackJack, it also
	 * returns true if the player is tied
	 */
	private boolean checkWinner(CardHand ch) {
		int dealerVal = this.checkValueHand(this.getDealer().getHand(), null);
		int playerVal = this.checkValueHand(ch, null);

		if (dealerVal == playerVal) {
			if (ch.getHand().size() == 2 && playerVal == 21) {
				System.out.println("Player has a natural Blackjack! Player wins!");
				return true;
			}
			if (this.getDealer().getHand().getHand().size() == 2 && dealerVal == 21) {
				System.out.println("Dealer has a natural Blackjack! Dealer wins!");
				return false;
			} else {
				System.out.println("Dealer and Player are tied! Player gets his bet back!");
				return true;
			}
		} else
			return dealerVal < playerVal;
	}

	/*
	 * This method is here to be called on the Stand method, and will proceed to
	 * draw cards for the Dealer until he gets more than 17 of value
	 */
	private int getDealerDraw() {
		int val = this.checkValueHand(this.getDealer().getHand(), null);

		while (val < 17) {
			Card drawnCard = this.deck.dealCard();
			this.getDealer().getHand().addCard(drawnCard);
			val += this.checkValueCard(drawnCard);
		}

		return val;
	}

	/*
	 * Method to prompt the player for an increased bet, and return that value
	 */
	private int getIncreasedBet() {
		boolean increaseBet = false;

		try {
			increaseBet = this.getYesNo("Would you like to increase your bet?");
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (increaseBet) {
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);
			int input = 0;

			while (true) {
				System.out.println();
				System.out.println(this.getPlayer().getName() + "  how much would you like to increase your bet by?");
				System.out.println();
				input = scanner.nextInt();

				if (input >= 0) {
					boolean enoughBal = this.getPlayer().removeBalance(input);
					if (enoughBal)
						return input;
					else {
						System.out.println("Please enter another bet value!");
					}
				}
			}
		}

		return 0;
	}

	/*
	 * Prompts player for their next move, and returns the string accordingly
	 */
	private String getNextMove(CardHand ch, int currentBet) {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		String input = "";

		while (true) {

			System.out.println();
			System.out.println(
					this.getPlayer().getName() + "  what would you like to do? ('double up'/'hit'/'stand'/'split')");
			input = scanner.nextLine();

			if (input.equalsIgnoreCase("stand"))
				return "stand";
			else if (input.equalsIgnoreCase("hit"))
				return "hit";
			else if (input.equalsIgnoreCase("double") || input.equalsIgnoreCase("double up"))
				if (this.getPlayer().getBalance() >= 2 * currentBet)
					return "double";
				else
					System.out.println("Cannot double your unless you have enough balance!");
			else if (input.equalsIgnoreCase("split")) {
				if (ch.getHand().size() != 2)
					System.out.println("Cannot split with less than 2 cards!");
				else {
					if (this.checkIfValuesEqual(ch.getHand().get(0), ch.getHand().get(1))) {
						if (this.getPlayer().getBalance() >= 2 * currentBet) {
							if (this.hasSplit)
								System.out.println("Cannot split more than once per round!");
							else
								return "split";
						} else
							System.out.println("Cannot split unless you have enough balance for the new hand!");
					} else
						System.out.println("Cannot split if the two cards in your deck are not of equal value!");
				}
			} else
				System.out.println("Invalid Input! Only 'double up'/'hit'/'stand'/'split' are allowed!");
		}
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public CardPlayer getDealer() {
		return dealer;
	}

	public void setDealer(CardPlayer dealer) {
		this.dealer = dealer;
	}

	public CardPlayer getPlayer() {
		return player;
	}

	public void setPlayer(CardPlayer player) {
		this.player = player;
	}

	public Deck getDeck() {
		return deck;
	}

	public void setDeck(Deck deck) {
		this.deck = deck;
	}

	/* =========== */
	/* Aux Methods */
	/* =========== */

	private void setMessages() {
		String welcome = "Welcome to BlackJack!!";

		String instructions = "============================= \n"
				+ "This game will begin by prompting players to enter their details, and to pick a dealer among them. \n"
				+ "Once the dealer is selected, the game plays, the Player will start with " + BlackJack.STARTER_AMOUNT
				+ " and will have to place a starting bet of " + BlackJack.STARTER_AMOUNT + " \n" + "\n" 
				+ "The two will then begin playing rounds of BlackJack, they will play rounds until the Player no longer has any money. \n"
				+ "Each round will consist of turns, where the player will pick between one of the different options, Hit, Stand, Split, Double Bet. \n"
				+ "\n"
				+ "Each of these follow the basic notions of BlackJack.\n"
				+ "\n"
				+ "If a player splits, and proceeds to Stand, they will be forced to Stand with their other hand. \n"
				+ "If a player BlackJacks or Busts with one of their hands, that hand leaves the game, if they only have one hand the round is over. \n"
				+ "If the dealer Busts, the player wins for every hands. \n"
				+ "============================= \\n";

		String goodbye = "Thank you for playing BlackJack! \n" + "\n" + "============================= \n"
				+ "A game by Victor Vicente \n" + "Made for BU CS611, Spring '21 \n" + "=============================";

		this.setWelcomeMSG(welcome);
		this.setInstructions(instructions);
		this.setGoodbyeMSG(goodbye);
	}

	/*
	 * This method is presented at the end of each game, to ask if the players want
	 * to switch roles.
	 */
	private void askReplace() {
		boolean replace = false;
		try {
			replace = this.getYesNo("Would you like to switch who the Dealer is?");
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (replace) {
			CardPlayer temp = this.getDealer();
			this.setDealer(this.getPlayer());
			this.setPlayer(temp);
		}

		System.out.println();
		System.out.println("Dealer's switched!");
		System.out.println();
	}

	/*
	 * Resets game decks
	 */
	private void resetGame() {
		this.dealer.resetHands();
		this.player.resetHands();
		this.setDeck(new Deck());
	}

	/*
	 * Compares card values
	 */
	private boolean checkIfValuesEqual(Card c1, Card c2) {
		return this.checkValueCard(c1) == this.checkValueCard(c2);
	}

	/*
	 * Checks value of a deck with an additional card option
	 */
	private int checkValueHand(CardHand ch, Card c) {
		int val = 0;
		int amountOfAces = 0;

		for (Card c1 : ch.getHand()) {
			int tempVal = this.checkValueCard(c1);

			if (tempVal == -1)
				amountOfAces++;
			else
				val += tempVal;
		}

		if (c != null) {
			int finalVal = this.checkValueCard(c);

			if (finalVal == -1)
				amountOfAces++;
			else
				val += finalVal;
		}

		for (int i = 0; i < amountOfAces; i++) {
			if (val + 11 > 21)
				val++;
			else
				val += 11;
		}

		return val;
	}

	/*
	 * Check the value of a card
	 */
	private int checkValueCard(Card c) {
		if (c.getIsFace()) {
			if (c.getFace().equals("ace"))
				return -1;
			else
				return 10;
		} else
			return c.getPip();
	}

}
