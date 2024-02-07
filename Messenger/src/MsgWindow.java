import java.awt.Container;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import java.util.Set;

/**
 * Provides a GUI front end for a logged-in user.
 */
public class MsgWindow extends JFrame
    implements KeyListener
{
  private static final String PROMPT = ">> ";

  private MsgUser myUser;
  private JComboBox<Object> buddiesList;
  // Starting with Java 7, JComboBox is type-specific.
  // If running an earlier release of Java, use
  // private JComboBox buddiesList;
  
  private JTextArea textArea;

  /**
   * Constructs a new window for a  given user.
   * @param u user that will own this window.
   * @param buddies  a set of buddies for this user.
   */
  public MsgWindow (MsgUser u, Set<MsgUser> buddies)
  {
    super(u.toString());

    addWindowListener(new WindowAdapter()
      { public void windowClosing(WindowEvent e) { myUser.quit(); }});

    myUser = u;

    buddiesList = new JComboBox<Object>(buddies.toArray());
    // In Java 6 or earlier, use
    // buddiesList = new JComboBox(buddies.toArray());

    JPanel talkTo = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    talkTo.add(new JLabel("Talk to:", JLabel.RIGHT));
    talkTo.add(buddiesList);

    textArea = new JTextArea(10, 20);
    textArea.setFont(new Font("Serif", Font.PLAIN, 14));
    textArea.setLineWrap(true);
    textArea.setWrapStyleWord(true);
    textArea.append(PROMPT);
    textArea.addKeyListener(this);
    JScrollPane areaScrollPane = new JScrollPane(textArea);
    areaScrollPane.setVerticalScrollBarPolicy(
        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    Container c = getContentPane();
    c.add(talkTo, BorderLayout.NORTH);
    c.add(areaScrollPane, BorderLayout.CENTER);

    int x = (int)(Math.random()* 500);
    int y = (int)(Math.random()* 300);
    setBounds(x, y, 300, 300);
    setVisible(true);
  }

  /**
   * Adds a buddy to this user's list of buddies.
   * @param u a buddy to be added to the list.
   */
  public void addBuddy(MsgUser u)
  {
    buddiesList.addItem(u);
  }

  /**
   * Removes a buddy from this user's list of buddies.
   * @param u a buddy to be removed from the list.
   */
  public void removeBuddy(MsgUser u)
  {
    buddiesList.removeItem(u);
  }

  /**
   * Displays a message on this window.
   * @param text a message to be displayed.
   */
  public void showMessage(String text)
  {
    textArea.append(text);
    textArea.append("\n" + PROMPT);
  }

  /**
   * Sends a message to the selected buddy.
   * @param text a message to be sent.
   */
  private void sendMessage(String text)
  {
    MsgUser u = (MsgUser)buddiesList.getSelectedItem();
    u.receiveMessage("[" + myUser.getName() + "] " + text);
    textArea.append(PROMPT);
  }

  /**
   * Processes the <code>Enter</code> key event on this window.
   */
  public void keyReleased (KeyEvent e)
  {
    int code = e.getKeyCode();

    switch(code)
    {
      case KeyEvent.VK_ENTER:
        String msg= textArea.getText();
        int tail = msg.lastIndexOf(PROMPT) + PROMPT.length();
        msg = msg.substring(tail).trim();
        if (msg.length() > 0)
          sendMessage(msg);
        break;
    }
  }

  public void keyPressed (KeyEvent e) {}
  public void keyTyped (KeyEvent e) {}
}
