package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class My_game extends JFrame implements ActionListener {
    JLabel heading, clock;
    Font font = new Font("", Font.BOLD, 30);

    JPanel mainPanel;
    JButton[] buttons = new JButton[9]; //for storing 9 buttons

    // game instance variable
    int[] gameChances = {2, 2, 2, 2, 2, 2, 2, 2, 2,};
    int activePlayer = 0;
    int[][] winning_positions = {
                               {0, 1, 2},
                               {3, 4, 5},
                               {6, 7, 8},
                               {0, 4, 8},
                               {1, 4, 7},
                               {2,4,6},
                               {2, 5, 8},
                               {2, 4, 6},
                               {0,3,6},
                               };
    int winner =2;       //flag
    boolean game_over = false;
    My_game() {

        System.out.println("creating instance of game");
        setTitle("Tic Tac Toe");
        setSize(500, 600);
        ImageIcon icon = new ImageIcon("src/images/icon.jpg");
        setIconImage(icon.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //program closes on exiting the frame
        GUI();
        setVisible(true);
    }

    private void GUI() {
        this.getContentPane().setBackground(Color.decode("#af0000"));
        this.setLayout(new BorderLayout());
        heading = new JLabel("TIC-TAC-TOE");
        heading.setFont(font);
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setForeground(Color.white);
        this.add(heading, BorderLayout.NORTH);

        // object of J label for clock
        clock = new JLabel("Clock");
        clock.setFont(font);
        clock.setHorizontalAlignment(SwingConstants.CENTER);
        clock.setForeground(Color.white);
        this.add(clock, BorderLayout.SOUTH);


        Thread t = new Thread(() -> {
            try {
                while (true) {
                    String date_time = new Date().toLocaleString();
                    clock.setText(date_time);
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                e.getStackTrace();
            }
        });
        t.start();


        //panel section
        mainPanel = new JPanel();
        // grid layout
        mainPanel.setLayout(new GridLayout(3, 3));
        for (int i = 1; i <= 9; i++) {
            JButton btn = new JButton();

            btn.setForeground(Color.decode("#cc0000"));
            btn.setFont(font);
            mainPanel.add(btn);
            buttons[i - 1] = btn;
            btn.addActionListener(this);
            btn.setName(String.valueOf(i - 1));
        }
        this.add(mainPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clicked_button = (JButton) e.getSource();
        String nameStr = clicked_button.getName();
        System.out.println(nameStr);
        int name = Integer.parseInt(nameStr.trim());
        if(game_over)
        {
            JOptionPane.showMessageDialog(this,"Game already over");
             return;
        }
        if (gameChances[name] == 2) {

            if (activePlayer == 1) {
                clicked_button.setIcon(new ImageIcon("src/images/cross.png"));
                gameChances[name]= activePlayer;
                activePlayer = 0;
            } else
            {
                clicked_button.setIcon(new ImageIcon(("src/images/circle.png")));
                gameChances[name] = activePlayer;
                activePlayer = 1;

            }
            for(int[] temp: winning_positions)
            {
                if((gameChances[temp[0]]== gameChances[temp[1]]) &&
                        (gameChances[temp[1]] == gameChances[temp[2]])&&
                        gameChances[temp[2]] != 2 )
                {
                    winner = gameChances[temp[0]];
                    game_over =true;

                    JOptionPane.showMessageDialog(null, "Player " + winner + " has won the game");
                    int i =JOptionPane.showConfirmDialog(this, "Do you want to play more?");
                    if(i==0)         //yes
                    {
                        this.setVisible(false);
                        new My_game();
                    }
                     else if(i==1)           // no
                    {
                        System.exit(233);
                    }

                    System.out.println(i);
                    break;
                }

             }
             //draw logic
            int count =0;
            for(int x: gameChances)
            {
                if(x==2)
                {
                    count++;
                    break;
                }
            }
            if(count==0 && !game_over)
            {
               JOptionPane.showMessageDialog(null, "Its a draw!!");
               int i =JOptionPane.showConfirmDialog(this, "Play more?");
               if(i ==0)
               {
                   this.setVisible(false);
                   new My_game();
               }
               else if(i==1)
               {
                   System.exit(1222);
               }
               game_over = true;
            }
        }

        else
            {
                JOptionPane.showMessageDialog(this, "parent already occupied");
            }
        }

    }
