package pv;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.*;

// Declaración clase "Game"
class Game {

  String name;
  int price;
  String genre;
  String platform;
  int quantity;
  int salesQuantity = 0;
  int salesTotal = 0;
  ArrayList<Sale> salesHistory = new ArrayList<>();

  public Game(
    String name,
    int price,
    String genre,
    String platform,
    int quantity
  ) {
    this.name = name;
    this.price = price;
    this.genre = genre;
    this.platform = platform;
    this.quantity = quantity;
  }

  public void decreaseQuantity(int amount) {
    if (quantity >= amount) {
      quantity -= amount;
      salesQuantity += amount;
      salesTotal += amount * price;
      salesHistory.add(
        new Sale(LocalDateTime.now(), this.name, amount, amount * price)
      );
    } else {
      throw new IllegalArgumentException("No hay suficientes juegos");
    }
  }

  @Override
  public String toString() {
    return (
      "Juego: " +
      name +
      "\nPrecio: " +
      price +
      "\nGénero: " +
      genre +
      "\nPlataforma: " +
      platform +
      "\nCantidad: " +
      quantity +
      "\n"
    );
  }
}

// Declaración clase "Sale", que incluye la información para ser agregada al historial de ventas
class Sale {

  LocalDateTime time;
  String gameName;
  int quantity;
  int total;

  public Sale(LocalDateTime time, String gameName, int quantity, int total) {
    this.time = time;
    this.gameName = gameName;
    this.quantity = quantity;
    this.total = total;
  }

  @Override
  public String toString() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
      "dd/MM/yyyy HH:mm"
    );
    return (
      "Tiempo: " +
      time.format(formatter) +
      "\nGame: " +
      gameName +
      "\nCantidad: " +
      quantity +
      "\nTotal: " +
      total
    );
  }
}

// Declaración clase "App", que vendría siendo al app que se vería en la interfaz gráfica
public class App {

  static ArrayList<Game> gameList = new ArrayList<>();
  JFrame frame;
  JPanel panel;

  public static final int GAMES_PER_PAGE = 3;
  public static final int HISTORY_PER_PAGE = 5;
  int currentPage = 0;
  

  public App(String role) {
    frame = new JFrame();
    panel = new JPanel();
    frame.setSize(200, 200);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setLocationRelativeTo(null);
    frame.add(panel);

    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

    // Acciones exclusivas administrador
    if (role.equals("Admin")) {

      // Boton de vender y su respectivo panel
      JButton sellButton = new JButton("Vender");
      sellButton.setBounds(10, 20, 80, 25);
      sellButton.setAlignmentX(Component.CENTER_ALIGNMENT);
      panel.add(Box.createRigidArea(new Dimension(0, 10)));
      panel.add(sellButton);
      sellButton.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {


            String gameName = JOptionPane.showInputDialog(
              frame,
              "Ingresa el nombre del juego:"
            );
            if (gameName == null) {
              return; 
            }


            String gamePrice = JOptionPane.showInputDialog(
              frame,
              "Ingresa el precio:"
            );
            if (gamePrice == null) {
              return;
            }


            String[] genres = {
              "Accion",
              "Accion-Aventura",
              "Puzzle",
              "Roleplay",
              "Simulador",
              "Estrategia",
              "Deportes",
              "MMO",
            };
            int genreChoice = JOptionPane.showOptionDialog(
              frame,
              "Escoge el genero:",
              "Eleccion",
              JOptionPane.DEFAULT_OPTION,
              JOptionPane.INFORMATION_MESSAGE,
              null,
              genres,
              genres[0]
            );

            if (genreChoice == -1) {
              return; 
            }
            String gameGenre = genres[genreChoice];


            String[] platforms = {
              "Celular",
              "Computador",
              "Playstation 5",
              "Xbox One",
              "Nintendo Switch",
            };
            int platformChoice = JOptionPane.showOptionDialog(
              frame,
              "Escoge la plataforma:",
              "Eleccion",
              JOptionPane.DEFAULT_OPTION,
              JOptionPane.INFORMATION_MESSAGE,
              null,
              platforms,
              platforms[0]
            );

            if (platformChoice == -1) {
              return; 
            }
            String gamePlatform = platforms[platformChoice];


            String gameQuantity = JOptionPane.showInputDialog(
              frame,
              "Ingresa la cantidad:"
            );
            if (gameQuantity == null) {
              return; 
            }

            gameList.add(
              new Game(
                gameName,
                Integer.parseInt(gamePrice),
                gameGenre,
                gamePlatform,
                Integer.parseInt(gameQuantity)
              )
            );
          }
        }
      );
      
      // Boton de reportes y su respectivo panel
      JButton reportsButton = new JButton("Reportes");
      reportsButton.setBounds(10, 100, 80, 25);
      reportsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
      panel.add(Box.createRigidArea(new Dimension(0, 10)));
      panel.add(reportsButton);
      reportsButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            JFrame reportsFrame = new JFrame("Reportes e Historial");
            reportsFrame.setSize(500, 500);
            reportsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            reportsFrame.setLocationRelativeTo(null);

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            int totalSalesQuantity = 0;
            int totalSales = 0;
            for (Game game : gameList) {
              totalSalesQuantity += game.salesQuantity;
              totalSales += game.salesTotal;
            }

            JLabel totalSalesLabel = new JLabel(
              "Cantidad Total de Vendidos: " + totalSalesQuantity
            );
            JLabel totalMoneyLabel = new JLabel(
              "Dinero Total Generado: " + totalSales
            );
            panel.add(totalSalesLabel);
            panel.add(totalMoneyLabel);

            ArrayList<Sale> allSales = new ArrayList<>();
            for (Game game : gameList) {
              allSales.addAll(game.salesHistory);
            }
            allSales.sort(Comparator.comparing(sale -> sale.time));
            Collections.reverse(allSales); 

            currentPage = 0;
            int start = currentPage * HISTORY_PER_PAGE;
            int end = start + HISTORY_PER_PAGE;
            for (int i = start; i < end && i < allSales.size(); i++) {
              JTextArea textArea = new JTextArea();
              textArea.setText(allSales.get(i).toString());
              panel.add(textArea);
            }

            JPanel buttonPanel = new JPanel();

            JButton backButton = new JButton("Volver");
            backButton.addActionListener(
              new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                  if (currentPage > 0) {
                    currentPage--;
                    reportsFrame.dispose();
                    reportsButton.doClick(); 
                  }
                }
              }
            );
            buttonPanel.add(backButton);

            JButton nextButton = new JButton("Siguiente");
            nextButton.addActionListener(
              new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                  if ((currentPage + 1) * HISTORY_PER_PAGE < allSales.size()) {
                    currentPage++;
                    reportsFrame.dispose();
                    reportsButton.doClick(); 
                  }
                }
              }
            );
            buttonPanel.add(nextButton);

            panel.add(buttonPanel);

            reportsFrame.add(panel);
            reportsFrame.setVisible(true);
          }
        }
      );
    }

    // Ahora estamos fuera de lo exclusivo del administrador
    // Boton de juegos y su respectivo panel
    JButton gamesButton = new JButton("Juegos");
    gamesButton.setBounds(10, 60, 80, 25);
    gamesButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(Box.createRigidArea(new Dimension(0, 10)));
    panel.add(gamesButton);
    gamesButton.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          JFrame gamesFrame = new JFrame("Tienda de Juegos");
          gamesFrame.setSize(500, 500);
          gamesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
          gamesFrame.setLocationRelativeTo(null);

          JPanel panel = new JPanel();
          panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

          int start = currentPage * GAMES_PER_PAGE;
          int end = start + GAMES_PER_PAGE;
          for (int i = start; i < end && i < gameList.size(); i++) {
            Game game = gameList.get(i);
            JTextArea textArea = new JTextArea();
            textArea.setText(game.toString());
            JButton buyButton = new JButton("Comprar");
            buyButton.addActionListener(
              new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                  String quantityString = JOptionPane.showInputDialog(
                    gamesFrame,
                    "Ingresa cantidad a comprar:"
                  );
                  int quantity = Integer.parseInt(quantityString);
                  int confirm = JOptionPane.showConfirmDialog(
                    gamesFrame,
                    "Estás seguro que quieres comprar  " +
                    quantity +
                    " copia(s) de " +
                    game.name +
                    "?"
                  );
                  if (confirm == JOptionPane.YES_OPTION) {
                    try {
                      game.decreaseQuantity(quantity);
                      textArea.setText(game.toString()); 
                    } catch (IllegalArgumentException exception) {
                      JOptionPane.showMessageDialog(
                        gamesFrame,
                        "No hay suficientes juegos",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                      );
                    }
                  }
                }
              }
            );

            panel.add(textArea);
            panel.add(buyButton);
          }

          JPanel buttonPanel = new JPanel();

          JButton backButton = new JButton("Volver");
          backButton.addActionListener(
            new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                if (currentPage > 0) {
                  currentPage--;
                  gamesFrame.dispose();
                  gamesButton.doClick(); 
                }
              }
            }
          );
          buttonPanel.add(backButton);

          JButton nextButton = new JButton("Siguiente");
          nextButton.addActionListener(
            new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                if ((currentPage + 1) * GAMES_PER_PAGE < gameList.size()) {
                  currentPage++;
                  gamesFrame.dispose();
                  gamesButton.doClick(); 
                }
              }
            }
          );
          buttonPanel.add(nextButton);

          panel.add(buttonPanel);

          gamesFrame.add(panel);
          gamesFrame.setVisible(true);
        }
      }
    );
    
    // Boton para poder volver a la selección de usuario
    JButton returnButton = new JButton("Volver");
    returnButton.setBounds(10, 100, 80, 25);
    returnButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(Box.createRigidArea(new Dimension(0, 10)));
    panel.add(returnButton);
    returnButton.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          frame.dispose();
          roleSelection();
        }
      }
    );

    frame.setVisible(true);
  }

  // Primer panel, en el que se escoge el rol/usuario
  public static void roleSelection() {
    String[] options = { "Admin", "Cliente" };
    int roleChoice = JOptionPane.showOptionDialog(
      null,
      "Eres un Administrador o Cliente?",
      "Escoge un Usuario",
      JOptionPane.DEFAULT_OPTION,
      JOptionPane.INFORMATION_MESSAGE,
      null,
      options,
      options[0]
    );

    if (roleChoice == -1) {
      System.exit(0);
    }

    String role = (roleChoice == 0) ? "Admin" : "Client";
    new App(role);
  }

  // Juegos de ejemplo que se colocan para que al iniciar la app, no esté vacio
  public static void main(String[] args) {
    roleSelection();
    gameList.add(
        new Game(
            "Halo", 
            15000, 
            "Action", 
            "Xbox One", 
            10
        )
    );
    gameList.add(
        new Game(
            "The Legend of Zelda: Breath of the Wild",
            60000,
            "Accion-Aventura",
            "Nintendo Switch",
            5
        )
    );
    gameList.add(
        new Game(
            "Super Mario Odyssey",
            60000,
            "Accion-Aventura",
            "Nintendo Switch",
            5
        )
    );
    gameList.add(
        new Game(
            "The Last of Us Part II",
            60000,
            "Accion-Aventura",
            "Playstation 5",
            3
        )
    );
    gameList.add(
        new Game(
            "Unpacking", 
            5, 
            "Puzzle", 
            "Computador", 
            12
        )
    );
    gameList.add(
        new Game(
            "Disco Elysium", 
            39, 
            "Roleplay", 
            "Computador", 
            7
        )
    );
    gameList.add(
        new Game(
            "The Sims 4", 
            39, 
            "Simulador", 
            "Computador", 
            8
        )
    );
  }
}
