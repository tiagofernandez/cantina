package cantina

import java.awt.*
import java.awt.event.*
import javax.swing.*

class CantinaView {

  private final CantinaController controller
  private final SystemTray systemTray

  private TrayIcon trayIcon

  CantinaView(CantinaController controller) {
    this.controller = controller
    this.systemTray = SystemTray.systemTray
  }

  void load() {
    SwingUtilities.invokeLater new Runnable() {
      public void run() {
        setLookAndFeel()
        setTrayIcon()
        refreshPopupMenu()
      }
    }
  }

  void unload() {
    systemTray.remove(trayIcon)
    System.exit(0)
  }

  private void setLookAndFeel() {
    try {
      UIManager.setLookAndFeel('com.sun.java.swing.plaf.windows.WindowsLookAndFeel')
      UIManager.put('swing.boldMetal', false)
    }
    catch (ex) {
      ex.printStackTrace()
    }
  }

  private void setTrayIcon() {
    def imagePath = 'images/food.gif'
    def imageURL  = Thread.currentThread().contextClassLoader.getResource(imagePath)

    if (!imageURL)
      throw new IllegalStateException("Image not found: $imagePath")

    trayIcon = new TrayIcon(new ImageIcon(imageURL).image).with {
      toolTip = 'Cantina?'
      addMouseListener(new MouseAdapter() {
        void mouseClicked(MouseEvent mouseEvent) {
          refreshPopupMenu()
        }
      })
      return it
    }
    systemTray.add(trayIcon)
  }

  private void refreshPopupMenu() {
    def popupMenu = new PopupMenu()
    rebuildGoingList(popupMenu)

    trayIcon.popupMenu = popupMenu.with {
      addSeparator()
      add(buildGoingMenuItem())
      add(buildNotGoingMenuItem())
      addSeparator()
      add(buildExitItem())
      return it
    }
  }

  private MenuItem buildGoingMenuItem() {
    def goingItem = new MenuItem('To dentro')
    goingItem.addActionListener({ event ->
      controller.setGoing()
      trayIcon.displayMessage('+1', 'Restaurante do main site, 11:50.', TrayIcon.MessageType.INFO)
    } as ActionListener)
    goingItem
  }

  private MenuItem buildNotGoingMenuItem() {
    def notGoingItem = new MenuItem('To fora')
    notGoingItem.addActionListener({ event ->
      controller.setNotGoing()
      trayIcon.displayMessage('-1', 'Ate a proxima!', TrayIcon.MessageType.NONE)
    } as ActionListener)
    notGoingItem
  }

  private MenuItem buildExitItem() {
    def exitItem = new MenuItem('Sair')
    exitItem.addActionListener({ event ->
      unload()
    } as ActionListener)
    exitItem
  }

  private void rebuildGoingList(popupMenu) {
    def goingList = controller.goingList
    if (goingList.empty)
      popupMenu.add(new MenuItem('(ninguem)'))
    else
      goingList.each { popupMenu.add(new MenuItem(it as String)) }
  }
}
