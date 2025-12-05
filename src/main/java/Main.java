import util.ColorUtils;

/**
 * <p>This class:
 * 1. Displays ASCII art animation on startup,
 * 2. Tests the database connection,
 * 3. Starts the login service.
 * 4. Launches role-specific menus after authentication.
 * @author sarah nauman
 */
public class Main {
    // ANSI color codes for animation
    private static final String RESET  = "\u001B[0m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RED    = "\u001B[31m";

    // Animation frames array
    private static final String[] FRAME = new String[111];

    static {
        FRAME[0] = """
                                                                                                                                              
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                          .. .                                                                    
                                                          .  .                                                                    
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                     
                """;
        FRAME[1] = """
                                                                                                                                              
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                          .. .                                                                    
                                                          .  .                                                                    
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                      
                """;
        FRAME[2] = """
                                                                                                                                              
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                          .. .                                                                    
                                                             .                                                                    
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                      
                """;
        FRAME[3] = """
                                                                                                                                              
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                           . .                                                                    
                                                             .                                                                    
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                      
                """;
        FRAME[4] = """
                                                                                                                                              
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                           . .                                                                    
                                                             .                                                                    
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                      
                """;
        FRAME[5] = """
                                                                                                                                              
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                           . .                                                                    
                                                             .                                                                    
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                          .                                                                           
                """;
        FRAME[6] = """
                                                                                                                                              
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                           . .                                                                    
                                                             .                                                                    
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                           ::.                                                                    
                                                           :==-=-- .                                                              
                                                           +*.**:                                                                     
                """;
        FRAME[7] = """
                                                                                                                                              
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                           . .                                                                    
                                                             .                                                                    
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                             -                                                                    
                                                            -=:=:=                                                                
                                                            -===#=                                                                
                                                           .-:--=*=                                                               
                                                           :+.:=-##                                                                   
                """;
        FRAME[8] = """
                                                                                                                                              
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                           . .                                                                    
                                                             .                                                                    
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                *-                                                                
                                                            .:-.-+                                                                
                                                             =- +*-+                                                              
                                                             ---:-=#*                                                             
                                                             :-:+=:-:=+                                                           
                                                            ::*%=#:--- ::                                                         
                                                             :-+:+--*-.   .                                                       
                                                            .-**-*%=-  :: ..                                                          
                """;
        FRAME[9] = """
                                                                                                                                              
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                          .. .                                                                    
                                                             .                                                                    
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                .                                                                 
                                                           ..:+=     .                                                            
                                                          ::-.+*#.:   .                                                           
                                                            ::-=-+##   .                                                          
                                                             ::.:---==*-                                                          
                                                             ::-++:+-*: ::.                                                       
                                                              =++--::-=  .                                                        
                                                              .%+-=*=*     =.                                                     
                                                              -+*-*=*=.                                                           
                                                             :=#===+#.                                                            
                                                            :-*%*:-=::-                                                               
                """;
        FRAME[10] = """
                                                                                                                                              
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                          .. .                                                                    
                                                             .                                                                    
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                .                                                                 
                                                           ..:+=     .                                                            
                                                          ::-.+*#.:   .                                                           
                                                            ::-=-+##   .                                                          
                                                             ::.:---==*-                                                          
                                                             ::-++:+-*: ::.                                                       
                                                              =++--::-=  .                                                        
                                                              .%+-=*=*     =.                                                     
                                                              -+*-*=*=.                                                           
                                                             :=#===+#.                                                            
                                                            :-*%*:-=::-                                                               
                """;
        FRAME[11] = """
                                                                                                                                              
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                         .+:                                                                      
                                                     =:--===-                                                                     
                                                      :*:+*-..                                                                    
                                                      :*:+-*+#                                                                    
                                                       =--:-:--==-::.   .                                                         
                                                      .*.-==.--#=.                                                                
                                                        :.*--=-:+=                                                                
                                                        :.=-+:+--=--                                                              
                                                          ..:--+==**-=                                                            
                                                             ::+#-.::                                                             
                                                             .:+##:.                                                              
                                                              .:=#+:.                                                             
                                                              .--*#::                                                             
                                                              .-=#%::                                                             
                                                              ==*%+::                                                             
                                                      .=:-  :=-+*%-:                                                              
                                                        =   ::+#%+::                                                              
                                                        :-:--+*##-.:                                                                 
                """;
        FRAME[12] = """
                                                                                                                                              
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                           :.  .                                                                  
                                                         .-=:-*++                                                                 
                                                        .-+---+  .                                                                
                                                        :=----+-  .                                                               
                                                      .=:==-:--::                                                                 
                                                     ::*=*-::-+. :.                                                               
                                                      : ==:*=-=.                                                                  
                                                        =++=====:                                                                 
                                                        .-*--:-=::                                                                
                                                         :=*%--+.                                                                 
                                                          :=#%-:                                                                  
                                                           :-#%-.                                                                 
                                                           ..-*%=:                                                                
                                                             :-#%-:                                                               
                                                             ::-##:.                                                              
                                                              .:=##:.                                                             
                                                       .:-:  .:=-+#:.                                                             
                                                          -  :-#=#%:.                                                             
                                                           ...:=*%#:.                                                             
                                                             :=+*%-:.                                                             
                                                            .:+##*-:                                                              
                                                            :**##-*                                                                   
                """;
        FRAME[13] = """
                                                                                                                                              
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                =....                                                             
                                                             :-=--+*=.                                                            
                                                           .=.+-:-+:                                                              
                                                          ::+-: .-+:                                                              
                                                        . :- -:-+-=:  .                                                           
                                                         -=-=-::-- -                                                              
                                                        ::-.-:=*: -                                                               
                                                        --#==--:   .                                                              
                                                        =*#+:=-:                                                                  
                                                        :*##--                                                                    
                                                        :=*#+:.                                                                   
                                                         --#%=:                                                                   
                                                         .:=#%--                                                                  
                                                          .:-#%-:.                                                                
                                                           ..-#%=.                                                                
                                                            .:-#%+.                                                               
                                                        :.   .:=#%-.                                                              
                                                       :=+  .=*:=#%:.                                                             
                                                          ... .=-*%:.                                                             
                                                              --=#%::                                                             
                                                             .-=##%::                                                             
                                                            .:-**%-:.                                                             
                                                            .:+#%#-:                                                              
                                                           .:+##%=..                                                                 
                """;
        FRAME[14] = """
                                                                                                                                              
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                      .                                                           
                                                                     :--==*                                                       
                                                                    -+:=#=   -                                                    
                                                                  :-=---++                                                        
                                                                .:+=-..-*=   .                                                    
                                                                -::+--*=-=  .                                                     
                                                              -=:.--.:-:.- .                                                      
                                                           :+=:++:-::% :.  -                                                      
                                                          --+*#::=-:=                                                             
                                                         --+*=:    :    .                                                         
                                                        :==#=-.          ..                                                       
                                                        -+##=..                                                                   
                                                        =*#*-:.                                                                   
                                                        =+##-:.                                                                   
                                                        :=*#*:..                                                                  
                                                    .   .:-#%+:.                                                                  
                                                  .-=.   -==*%+:                                                                  
                                                   =--. -++:=#%-:                                                                 
                                                      .=-  .:-%%=:                                                                
                                                            =:-*%+..                                                              
                                                             ::=*%-:                                                              
                                                              ::=#%:.                                                             
                                                              :-=*%:.                                                             
                                                              -==*%:.                                                             
                                                            .:-=*#%:                                                              
                                                           ..:=+*%+-                                                              
                                                           .:-+*%#::                                                              
                                                           .:***%+-.                                                                  
                """;
        FRAME[15] = """
                                                                                                                                              
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                      .:----   -                                                  
                                                                       -.-=-                                                      
                                                                      .:-=-==   .                                                 
                                                                     .-:-:+#+                                                     
                                                                     -+-:-:-==                                                    
                                                                    -:.*::#+:+::                                                  
                                                                  --: ----+:  .-                                                  
                                                               .-=-++-::.*  - .                                                   
                                                             :=-*+*-::-::                                                         
                                                           :===#*-=.   :                                                          
                                                          --+#*-.             .:                                                  
                                                        .-=**=:.                                                                  
                                                 .-     :=+#+-.                                                                   
                                                 :*-    :=*%++                                                                    
                                                .**-   -=+#*--.                                                                   
                                                   : .+#+*##-:.                                                                   
                                                   .++. :-*#*:..                                                                  
                                                        .-:#%+:                                                                   
                                                         .:=*%*:                                                                  
                                                          ..=#%=:                                                                 
                                                           ..-*%=:                                                                
                                                            =:-*%=..                                                              
                                                             ::-*%=:                                                              
                                                              ::=#%:.                                                             
                                                              --=*%-.                                                             
                                                            ..=-+*%:.                                                             
                                                           ...-=*#%:.                                                             
                                                           .::=+#%+:.                                                             
                                                           .:-+#%#-:                                                              
                                                           .:+*##=+                                                                   
                """;
        FRAME[16] = """
                                                                                                                                              
                                                                                                                                  
                                                                                                                                  
                                                                    --.       :                                                   
                                                                   :::+#==:                                                       
                                                                    :..--#%+:.                                                    
                                                                     .::::-=:.==:.                                                
                                                                      :.=-:-#=+..                                                 
                                                                        ::-:=*:+.  .                                              
                                                                       :--+==+-        .                                          
                                                                       :-:=+-=-                                                   
                                                                     .:=#*-:*#:                                                   
                                                                    -:+*=:.:. -                                                   
                                                        .        .==+*#-:                                                         
                                                       +=+.    --+=#*-:                                                           
                                                       -.    :==***=-.                                                            
                                                       :-=*##=+*#=:.                                                              
                                                         .-==*#=:                                                                 
                                                        .--+*+-.                                                                  
                                                        :-*#*:.                                                                   
                                                       .-=*%=.                                                                    
                                                       :-+##-:.                                                                   
                                                       .=+##=-.                                                                   
                                                        -=*#*-..                                                                  
                                                        :=-#%*:                                                                   
                                                         :-=*%*:.                                                                 
                                                          .:+#%+:                                                                 
                                                           ::-*%*:                                                                
                                                            -:-*%+-                                                               
                                                             :.-*%=:                                                              
                                                              ::=#%:.                                                             
                                                              ---*%-.                                                             
                                                           ...--=*%::                                                             
                                                          ....-=*#%:.                                                             
                                                          .:::==*%=:                                                              
                                                           ::-+*##::                                                              
                                                          .::=+#*-:.                                                                  
                """;
        FRAME[17] = """
                                                                              :   :.                                                          
                                                                           ..                                                     
                                                               -    :          .                                                  
                                                             .-:*=-=***:::::      :  .                                            
                                                              .....:-:-:*+-+=-=*                                                  
                                                                   .::::-==++=                                                    
                                                                    ...-:+:==--                                                   
                                                                       ::-==**#=.                                                 
                                                                        :*++---=.                                                 
                                                                       .-=*.:..                                                   
                                                                      .-+*=:.                                                     
                                                                     ::*#*:.                                                      
                                                            =#+     -:+#*:.                                                       
                                                           .+-.. :-==*#-..                                                        
                                                              :-*+=#*=:.                                                          
                                                             :-=**#=:.                                                            
                                                           -==+*#=-.                                                              
                                                         .==+*#--                                                                 
                                                        ::=+*+-.                                                                  
                                                        :-+#*-.                                                                   
                                                       :-=*%=*.                                                                   
                                                      .=++##--.                                                                   
                                                       .=+##+-.                                                                   
                                                        :=*##-:                                                                   
                                                        :--#%+:.                                                                  
                                                         ::=*%*-                                                                  
                                                         .::=#%+-                                                                 
                                                           ..-#%+-                                                                
                                                            -.-*%+..                                                              
                                                             ::-*%=:                                                              
                                                              ::=#%:.                                                             
                                                            ..:--*%:.                                                             
                                                           ...--=*%..                                                             
                                                          .:.:-=+#%:.                                                             
                                                          .:::==#%-.                                                              
                                                      ... .-=:=*#*-.                                                              
                                                      .*:.:::=+*#-:                                                                   
                """;
        FRAME[18] = """
                                                                     .                                                                        
                                                       .      .::-..:                                                             
                                                         .:--::-----:-=+-+                                                        
                                                     ----+--*%+:+==-=---+--.                                                      
                                                    :.=.--:...:.-:.:--= :=.:                                                      
                                                               ...   .-:-.=:                                                      
                                                             .        .:.=::.                                                     
                                                                       .::+.:                                                     
                                                                        --+:-                                                     
                                                                .:.    :==#::                                                     
                                                                 :.   .:=*=:.                                                     
                                                                   :=*-=%+:.                                                      
                                                                   .:=+%*:.                                                       
                                                                 :==*%#=-.                                                        
                                                               --=*#%=-.                                                          
                                                             -=-+##=-.                                                            
                                                          .====#%=-.                                                              
                                                         .-==##--.                                                                
                                                        :-=+#*-.                                                                  
                                                        --+##-.                                                                   
                                                       --=#%=*                                                                    
                                                      .:=*##--.                                                                   
                                                      .:=*##+-..                                                                  
                                                       .-=*#*-:                                                                   
                                                        .-=*%*:                                                                   
                                                         ..=#%*:                                                                  
                                                         .::-#%+:                                                                 
                                                           ::=*%+:                                                                
                                                            ..-#%+.                                                               
                                                             :.-#%-..                                                             
                                                              ::-##:.                                                             
                                                           ...---+%:.                                                             
                                                        .:....---*#:.                                                             
                                                         +:..:-++%*.                                                              
                                                         .====+**%-.                                                              
                                                          ::::=+%+-                                                               
                                                        .::::=+##-.                                                                   
                """;
        FRAME[19] = """
                                                                   .                                                                          
                                                    .    ..-:....:=                                                               
                                                  .    :-----+=-:-=::.                                                            
                                                   ..=-+** ==-=-=-..:**+.                                                         
                                                 --=-=:#*=:::..:::::--=:=*.                                                       
                                               .-:*+-=:...::-:    -  ..:=:-:                                                      
                                                .....                 .::=-:.                                                     
                                                                        ::+:-                                                     
                                                                 .=-    -=+-=.                                                    
                                                                   :.-=--=*::                                                     
                                                                      .:+*+:.                                                     
                                                                     :.+#*:.                                                      
                                                                   .-:+%#-:                                                       
                                                                .:-==*%=:                                                         
                                                              .-===##+-.                                                          
                                                            .=-=**#=-.                                                            
                                                         ..==-+*%=-.                                                              
                                                         :-=+*#=-.                                                                
                                                        :-=+**-.                                                                  
                                                        --+##-.                                                                   
                                                      .:-=#%==                                                                    
                                                      :-+*##--.                                                                   
                                                      .:=+##+-:.                                                                  
                                                        --+##::                                                                   
                                                        .--#%+:.                                                                  
                                                         ::=+%+:                                                                  
                                                        ..::=#%=:                                                                 
                                                           ..-#%=.                                                                
                                                            -:-*%=.                                                               
                                                            .::-+%-:                                                              
                                                           ...::-##:.                                                             
                                                        .:+...:--*%..                                                             
                                                        ..:--++==*%.                                                              
                                                         .:.:::=+#+.                                                              
                                                         .::::-=*#-.                                                              
                                                         .::::=*#*-                                                               
                                                       ..::::=+*#-.                                                                   
                """;
        FRAME[20] = """
                                                                .                                                                             
                                                  .     . +  .                                                                    
                                                . . :::-.=+=---===-=:.                                                            
                                                  :----=---==+:=--:::+#=-.                                                        
                                              : :-+:=+++==--+::-::::-==:-+.                                                       
                                             -:---*#%=*+-..::.       .::+::=.                                                     
                                             ::---=#+--.               .:-==-                                                     
                                           ..:.=*=               : :.--:::*:=.                                                    
                                          .-+:=*-      .               .--+--                                                     
                                            ..                        .:==#:-                                                     
                                                                     .::+*+:                                                      
                                                                     :.=%*:.                                                      
                                                                   :-=+%#-:                                                       
                                                                .-==+##=:                                                         
                                                              .-==*#%+-.                                                          
                                                            .===+#%+=.                                                            
                                                         ..=+==#%==:.                                                             
                                                         :--=##=-..                                                               
                                                        :-=+**-..                                                                 
                                                       :--+#*-:                                                                   
                                                      .:-*%%=:                                                                    
                                                     .::=*##--:                                                                   
                                                      .:=+*#=-:.                                                                  
                                                       .:-*#*::                                                                   
                                                        .-=#%+:.                                                                  
                                                       ..:.=#%+.                                                                  
                                                       ...::-#%=:                                                                 
                                                           :.=*%=.                                                                
                                                        .  ..:-#%=:                                                               
                                                       ++- .-=#-#%-:..                                                            
                                                         .-=:.::-#*::.                                                            
                                                         .....:--*#:.                                                             
                                                       .......:==##:                                                              
                                                         .::::-=+%-.                                                              
                                                         :::.:-=*%::                                                              
                                                         .::::=*#+-                                                               
                                                        :::::-+**::                                                                   
                """;
        FRAME[21] = """
                                                             .                                                                                
                                                .     :                                                                           
                                               .     .+.---=-==+=+--=-+                                                           
                                              .    .::=+##*+**=---+:-++---:                                                       
                                               --+::--==+*=---- ::--:-=:.*=.                                                      
                                              ::=--+*=++=-.         ...:----                                                      
                                           .::-=---++-:. .-      :: .:.::=--:                                                     
                                           .----***=-.             .   .::+:-.                                                    
                                           :::----.      .:            .--+--.                                                    
                                           :-:-=:                     .--=*:-                                                     
                                          :-*::*+ :                  .:-+**::                                                     
                                           :-.                      .:-+##::                                                      
                                                                   :--*%#-:                                                       
                                                                .-=-+#%+:                                                         
                                                              .-=-=*%+-:                                                          
                                                            .==-+#*+-.                                                            
                                                        ..:===*+#=+:.                                                             
                                                         :-==*#+-...                                                              
                                                        :-=+#*-. .                                                                
                                                      ..:-*##-..                                                                  
                                                      .:-+#%=:..                                                                  
                                                     .:-=+##--:.                                                                  
                                                      .:-+##=-:.                                                                  
                                                       .:-*#*-.                                                                   
                                                        .--*%+:                                                                   
                                                       ..::=#%+.                                                                  
                                                      .  ..:=#%=.                                                                 
                                                    .=+-  .-*=*%=.                                                                
                                                       .:.=::.-*%=.                                                               
                                                         ....:::#%-....                                                           
                                                          . ..::-%*::.                                                            
                                                       .......:--*%:.                                                             
                                                       .......:-=*#:                                                              
                                                         :.:::-=*#=:                                                              
                                                        .:::::-=##:.                                                              
                                                        .:::::=*#*.                                                               
                                                       .::.::-*##:.                                                                   
                """;
        FRAME[22] = """
                                                                                                                                              
                                                              .--+-::.                                                            
                                             .          ::-====+===+++:                                                           
                                             .    +.::==##*=**==---::=+=-.                                                        
                                            :    -+****##*---::.----:-=-:*-..                                                     
                                             . .==-+=*+=-.     .  ::..::=::=.                                                     
                                           :.:-=::**--=.      .:.::  .:::----.                                                    
                                            -:+:-:=+::.. .             ::-+:=.                                                    
                                           ---+.--:-=.                 :--=::.                                                    
                                          =::-::**#                  .:--=*--                                                     
                                          .--:==:+-                  .:-+**::                                                     
                                           :++.+**  .              ..:-=%#::                                                      
                                            =*--:=-=               --=+%#-:                                                       
                                             =:+=..             .-=-+%#+:                                                         
                                              ---             .==-*#%+-.                                                          
                                                            .-=-=##+=:                                                            
                                                        ..:-==*#*+=:.                                                             
                                                         :-==+%=-...                                                              
                                                       .:==+%*-....                                                               
                                                      ..--*#*-..                                                                  
                                                     ..:-+#%=::.                                                                  
                                                     ..:=*##--:.                                                                  
                                                      .:-+##--:.                                                                  
                                                   ..  .:-+#*-.                                                                   
                                                  :=-.  :--#%+:                                                                   
                                                  .+-:..-**=*%+.                                                                  
                                                     .-*-.::-#%=.                                                                 
                                                         ..::=*%=.                                                                
                                                         .:::.-#%=                                                                
                                                         ...:-:-##-....                                                           
                                                         ....:::-%+::..                                                           
                                                      ........:-=*%:.                                                             
                                                        ......:==*#:                                                              
                                                        ...:..-++#-:                                                              
                                                        .:.:::-=*#:.                                                              
                                                        .:::::=*%*.                                                               
                                                       .::::--+##:.                                                                   
                """;
        FRAME[23] = """
                                                                                                                                              
                                                           :-*+-:--:                                                              
                                                        ::==-=++++-==:.                                                           
                                                   .--==*#+-#*=--=-:.=+-:..                                                       
                                                +::-***##*-=---.::=-:=+-:*-..                                                     
                                           .   :*++*=*=-.     ..::.....:-=:-..                                                    
                                           .  :-*-===+:.    ::..:.   .:::==:-.                                                    
                                            ::=+--++**-                -:-+:-:.                                                   
                                          :---+.::++::.-               :--=--.                                                    
                                           .+*=*:.+-+.               .:--=*--                                                     
                                           -:---+*+=                 .:-+**:.                                                     
                                          ::=-:==-=+                .:-+##::.                                                     
                                            :::::*##. .           .:--*%#-.                                                       
                                             .++:::-==+.        .:=-=*%=-.                                                        
                                              .:==+++         .-===#%+-.                                                          
                                                 .==-        ===+#*+-:.                                                           
                                                       ...:-==**#=:::                                                             
                                                         .-=+##=-...                                                              
                                                      .::-=+#+-:...                                                               
                                                      ..--+#*-:.                                                                  
                                                     ..:-=*%==:.                                                                  
                                                  :  ..:-*##-::.                                                                  
                                                 .-+  .:=+##=-:.                                                                  
                                                 .-+:  -+**#*-..                                                                  
                                                    ---=-:-#%=:                                                                   
                                                     ....:.=*%=:                                                                  
                                                         .:.=#%-.                                                                 
                                                         .:::-*%=.                                                                
                                                        ...:-:-*%-.                                                               
                                                         ..::::-*#:.....                                                          
                                                         .  .:::-%+:::.                                                           
                                                        ......:--*#::                                                             
                                                        ......:-=*#.                                                              
                                                        ...:..:=*#-.                                                              
                                                        ...:.:-=##-                                                               
                                                        .:::::=*#*-                                                               
                                                      ..:::---*##-                                                                    
                """;
        FRAME[24] = """
                                                                                                                                              
                                                      :.-=-::-=.:...                                                              
                                                   .....:-====++===+-:..                                                          
                                                 .::---=#*+=#*==-=--.+*=-:.                                                       
                                                .::-+#*%#*--:-::-:-:-==:-*=...                                                    
                                           . .=:::+*=#=-: .  ::........:-=:=...                                                   
                                           . .=:=*:+-:....*:...      ..::==:=:.                                                   
                                           ..:-+*=+*=..               .::-+:=:.                                                   
                                            =:-+*-*+-- -..            .---=-=:.                                                   
                                            :=++-:*-=.:      .      .::--=*-=.                                                    
                                            .-==-:-:-=              ..::+*+-.                                                     
                                            :=**-#=:=+:             .::-%#:..                                                     
                                             .:#=**=-%*           .--=+%#-:                                                       
                                             :-::-=.+#%#++*.     :==+%%+:..                                                       
                                              .---==:-::. :   .-=-+#%+-.                                                          
                                                  ..:++=+.   -+==%#+-:.                                                           
                                                       .+:::==*#*=-::.                                                            
                                                       ...-==+#=-....                                                             
                                                  ... :.:==+%*-.....                                                              
                                                  *== .:--*#*-:..                                                                 
                                                 .*+ ...=+#%+=::                                                                  
                                                   - .-+#*##--..                                                                  
                                                   .-*::-+##--..                                                                  
                                                       .:-+#*-:.                                                                  
                                                     . .::-*%+:.                                                                  
                                                       ..::-*%=:.                                                                 
                                                    . . .:::-*%=.                                                                 
                                                       ..::-:=*%-.                                                                
                                                        ..::-.-#%-    ..                                                          
                                                         .:::-::##-.....                                                          
                                                         . .:::.-#+::..                                                           
                                                       .......:--*#::                                                             
                                                        .....::-=*#..                                                             
                                                        ....::-=*#-.                                                              
                                                        .....:-+*#:                                                               
                                                       .::::::=*#+-                                                               
                                                      ..::----=*+-                                                                    
                """;
        FRAME[25] = """
                                                                                                                                              
                                                      :.-=-::-=.:...                                                              
                                                   .....:-====++===+-:..                                                          
                                                 .::---=#*+=#*==-=--.+*=-:.                                                       
                                                .::-+#*%#*--:-::-:-:-==:-*=...                                                    
                                           . .=:::+*=#=-: .  ::........:-=:=...                                                   
                                           . .=:=*:+-:....*:...      ..::==:=:.                                                   
                                           ..:-+*=+*=..               .::-+:=:.                                                   
                                            =:-+*-*+-- -..            .---=-=:.                                                   
                                            :=++-:*-=.:      .      .::--=*-=.                                                    
                                            .-==-:-:-=              ..::+*+-.                                                     
                                            :=**-#=:=+:             .::-%#:..                                                     
                                             .:#=**=-%*           .--=+%#-:                                                       
                                             :-::-=.+#%#++*.     :==+%%+:..                                                       
                                              .---==:-::. :   .-=-+#%+-.                                                          
                                                  ..:++=+.   -+==%#+-:.                                                           
                                                       .+:::==*#*=-::.                                                            
                                                       ...-==+#=-....                                                             
                                                  ... :.:==+%*-.....                                                              
                                                  *== .:--*#*-:..                                                                 
                                                 .*+ ...=+#%+=::                                                                  
                                                   - .-+#*##--..                                                                  
                                                   .-*::-+##--..                                                                  
                                                       .:-+#*-:.                                                                  
                                                     . .::-*%+:.                                                                  
                                                       ..::-*%=:.                                                                 
                                                    . . .:::-*%=.                                                                 
                                                       ..::-:=*%-.                                                                
                                                        ..::-.-#%-    ..                                                          
                                                         .:::-::##-.....                                                          
                                                         . .:::.-#+::..                                                           
                                                       .......:--*#::                                                             
                                                        .....::-=*#..                                                             
                                                        ....::-=*#-.                                                              
                                                        .....:-+*#:                                                               
                                                       .::::::=*#+-                                                               
                                                      ..::----=*+-                                                                    
                """;
        FRAME[26] = """
                                                                      ....  .                                                                 
                                                 .::.:::--::::::::..                                                              
                                                .-==-:--======++++-=+:...                                                         
                                                 .:--=-=*##=#*+=-=*--**--:.....                                                   
                                                .::-*#*###----:::::::-=+:*+::..                                                   
                                              .::=#*+#+-::--.....  ...:-=-.=::.                                                   
                                            ..:-=*:*-:===:::....     .:::==--::                                                   
                                            .::=*=-:::-:              .-:-+:=-:.                                                  
                                            ::*=##+*=:     ..        .::-===-::                                                   
                                            :-+=+++*=..-=.          ..:--=*--.                                                    
                                           .:-*+-*--:+=-# .        ..:--+**::                                                     
                                            .:-+#.++--:-+    .      :::+##::.                                                     
                                              :+=+===-+.-+*-      .:--*%#-:.                                                      
                                                .:-*=+=+*=+*=.--:--==*%+:.                                                        
                                                .-:.-=-=-:-++=-*===##+=:                                                          
                                                  .=.-=*+#*+=+==**%=-:.                                                           
                                                   :+=*...:+-++##==:::                                                            
                                                    --..::--+##-:..                                                               
                                                    ---++#+*#+--...                                                               
                                                    -+-::=+%*-::..                                                                
                                                    ....-+#%==:..                                                                 
                                                     ..:+*#*-=:.                                                                  
                                                      ..-+##--:.                                                                  
                                                     ..:--+#+-:.                                                                  
                                                      ...::#%=...                                                                 
                                                      ...::=*%=..                                                                 
                                                       ...::=#%-.                                                                 
                                                        :::::-#%=.                                                                
                                                       .:::--:-+%-......                                                          
                                                         :::--:-*#-::....                                                         
                                                           ::-::=%+-:..                                                           
                                                       ....::::--*#-:                                                             
                                                        ..:::::==#*:.                                                             
                                                        ....:::=*#-:                                                              
                                                       .....:--=+#..                                                              
                                                       ::--:--=+*-:                                                               
                                                      ..::--=-++=:                                                                    
                """;
        FRAME[27] = """
                                                                      ........                                                                
                                                .:...:--:::::::--::.                                                              
                                               .-===::-*-==--=+++=+---...                                                         
                                                .:----=*#**=#*+=-===:*#*+-::....                                                  
                                               ..::=+**%#**-=-:-::--===-=#=:..:                                                   
                                             ..::=#+=*=-:=-......  ...:-+-.=:.:                                                   
                                            .::-+*-+=::...-....       :::-+-=::.                                                  
                                            :-:=*:=-:=-  .           ..-:-*:+-:.                                                  
                                            :--*=+--::: .  .        .:::-=+-=-:.                                                  
                                            :==+===**: :=           ..:--=*--:.                                                   
                                           ..-*==*#.=-+-==.         .:-=+*#::                                                     
                                            .:-*=.-=+-=---    .    .:::+##::                                                      
                                              .::+-:*:.-:==-=     .:--+%#-:..                                                     
                                               .::-+=+-=*=-+*-:.--===#%=-. .                                                      
                                                 .-#+=+*+=:=+=+=*+*##+-:.                                                         
                                                  ..::=+=*+*++=-*##+=:..                                                          
                                                   ..=+....+=+##%==:::.                                                           
                                                    .---===-=##=-:..                                                              
                                                    .++---+*#+--:..                                                               
                                                   ....:--=%+-::...                                                               
                                                   .....-*%%===..                                                                 
                                                     ...=*#*-=:..                                                                 
                                                     . .:+##=-:.                                                                  
                                                    ...::-*#*-:..                                                                 
                                                      ...:-#%=:...                                                                
                                                      ...::=#%-:..                                                                
                                                       ....:-#%-:                                                                 
                                                       :::::.-#%-.                                                                
                                                       :.::::--*%-....                                                            
                                                        .:::--.=*#::::...                                                         
                                                         ..:-:::=%=::.                                                            
                                                        ...::::--*#::.                                                            
                                                         ..::.:+=#*:.                                                             
                                                        ...:::-=+%::                                                              
                                                       .....---=+#-.                                                              
                                                       ::--:--=+#--                                                               
                                                       .:---+-+*=-                                                                    
                """;
        FRAME[28] = """
                                                           ...       ..........                                                               
                                                .:..:-:...-:----:::...                                                            
                                              .=+=--::=--====+=++=+---::.      .                                                  
                                              :.-----==*##+=**+===-=.=#*=--:....                                                  
                                              . ::--**+%#*+:=::::---=+=-.*=:..:                                                   
                                             ..::-#+=*===:......   ....-+=-+:::                                                   
                                           ..:::+*-++::::-.. ...      :::=+:--:.                                                  
                                           .:-:=#:=-*+::..-.         ..::-+::-:.                                                  
                                            -:-*-:=--.. .:          ..::--+-:::.                                                  
                                         .  :++*=--++-.:.:.        ...:-==*--:.                                                   
                                           ..-*=====**-+---=      ..:--=++#:-.                                                    
                                           ..:-+---*:==++*--    ..:::::+%#::                                                      
                                              .::++- -- ::--==:  -.:--+%#:. .                                                     
                                               ..::=+--:*#+=+%+..:===##=-. .                                                      
                                                 ..:..=*%%#--++*#=##%+--.                                                         
                                                  .:.::=++*+++==+##+-::.                                                          
                                                  ...:------+=+*--=:.:.                                                           
                                                    .:====++=##=-..                                                               
                                                    ..:-=-=*#+=-..                                                                
                                                    ...:--=#+--::..                                                               
                                                    ....-+%#==--..                                                                
                                                     ...=*#*-=-:.                                                                 
                                                     .::-+##--:.                                                                  
                                                       .:-*#*-:..                                                                 
                                                       ..:-#%=:.                                                                  
                                                       ...:=#%-...                                                                
                                                       ....:-#%=:.                                                                
                                                       ....:.-*%=.. ..                                                            
                                                      ..::.:::-*%-:...                                                            
                                                        .:::--:-*#:-::.                                                           
                                                         .::--::=#+::.                                                            
                                                         .:::::--*#:::                                                            
                                                        ...::.:=-#+:..                                                            
                                                        ...::::-+#-..                                                             
                                                       .:..:---=+*-                                                               
                                                      .::--:---+#=.                                                               
                                                       .::::--+#--                                                                    
                """;
        FRAME[29] = """
                                                         .....      ...::::....                                                               
                                                .:::-:::::-::----:::..                                                            
                                              .=:=-::----=====++++=+-:::..     ..                                                 
                                             ::+-------=*##=#*+=-=+::+*+-:::...                                                   
                                             ...:---##*##*----:-::=--==*:*+::.:                                                   
                                             ..::-*#+*+==:......   ...:--=.=:::.                                                  
                                            .:-:+#:*-::.-...   .     .:::==:--::                                                  
                                          .:.-:-*-=-*-::.. ..       ...::-+:==-..                                                 
                                           .-:-==:--:..   -        ...::-==---:.                                                  
                                            -=+++:=-==:.:.=         ..:--=*--:..                                                  
                                           .:-*#+-*==++--*===     .::---+*+:-.                                                    
                                          ..:--==+:=+.=+==---   .::::-:+##:: .                                                    
                                            ..::--=.:-*+:----+=. .:---*#*-. .                                                     
                                               ...:-+---+***=+#*.:=-+#%+:..                                                       
                                                 ..:-..-#%%*-=**##-+%+--.                                                         
                                                  .:::-=:=++*+=+++*==::..                                                         
                                                  ...::+=-+=+=*=*--:..:                                                           
                                                    .::---:==**=-..                                                               
                                                   ....-=--+#+--:..                                                               
                                                   ....::-*#+=--:..                                                               
                                                    ....-=#%==--:..                                                               
                                                    ....=+#*-=-:.                                                                 
                                                     ...-+##--:.                                                                  
                                                       .:-*#+-:.                                                                  
                                                       ..:-#%=:.                                                                  
                                                       ...:=*%=.:.                                                                
                                                       ....:-*%-..                                                                
                                                      .....::-*%=....                                                             
                                                      .....::.-%%=:..                                                             
                                                       .::::--::##--:..                                                           
                                                         .::::::-%=--..                                                           
                                                        ..:::::--*#:::.                                                           
                                                         .:::.:-=*+-...                                                           
                                                        ...::::=+*::.                                                             
                                                       .:.::---+++:.                                                              
                                                      .::------+*::                                                               
                                                      ..::::--=*=-.                                                                   
                """;
        FRAME[30] = """
                                                           ....     ..::::::::..                                                              
                                                .:::::::::-:-=----::..                                                            
                                                -::------=====*=++=+=-::::.   ..                                                  
                                           .--*=---=====*#*=#*+==+*-:++==-::...                                                   
                                             :..:--=#%*###-=--:---==-=+=-*=--::                                                   
                                            ..:::-#*=*+--:.:.....  .:.:-==:+:::.                                                  
                                           ..-::+*-*-::-:...         .:::==:=--.                                                  
                                         ....::-*:=++:.:....       ....---+:==-..                                                 
                                          .:-:===:-=::..   :       ....:-==--=:.                                                  
                                          ..-=++*:-=--:..= :.       ..:--=*-=-:.                                                  
                                           .:-*#*--===-=:-*-=+   ..-----+++:-:.                                                   
                                          ..:--+*=::-*:---*+--: .--:--:*##::                                                      
                                            ..:.--:=+.*+=:--:-++:..=--*##-:.                                                      
                                              ..:.:-=#+::-+*#:=##==--#%=-.                                                        
                                                 .::..:--=###*=*%%#*=+=-:.                                                        
                                                ...:::::*=-==*+-+=++--:...                                                        
                                                 ....:::::.-+=#+*+-:....                                                          
                                                    .:--:-:==*#=:.-                                                               
                                                    ..:-=-=+*+--:..                                                               
                                                   ...::-=*#+===::..                                                              
                                                     ...:=*%==--:..                                                               
                                                     .::=*#*-=--:.                                                                
                                                     ...-+##--:..                                                                 
                                                       .:-*#+:::.                                                                 
                                                       ..--#%=:.                                                                  
                                                        ..:=*%=.::                                                                
                                                       ...::=##=:...                                                              
                                                      .....::-*%-....                                                             
                                                     ......:-.-##-::...                                                           
                                                      ....:---::##-::..                                                           
                                                        .::::-::=%+--:.                                                           
                                                         .::::::-+#-:...                                                          
                                                         .:::..--*+:....                                                          
                                                        . .::::=+*::...                                                           
                                                      .:.::----=**:.                                                              
                                                     ..:-------++::                                                               
                                                      .::::::-=+-::.                                                                  
                """;
        FRAME[31] = """
                                                           ....     ..::::::::..                                                              
                                                .:::::::::-:-=----::..                                                            
                                                -::------=====*=++=+=-::::.   ..                                                  
                                           .--*=---=====*#*=#*+==+*-:++==-::...                                                   
                                             :..:--=#%*###-=--:---==-=+=-*=--::                                                   
                                            ..:::-#*=*+--:.:.....  .:.:-==:+:::.                                                  
                                           ..-::+*-*-::-:...         .:::==:=--.                                                  
                                         ....::-*:=++:.:....       ....---+:==-..                                                 
                                          .:-:===:-=::..   :       ....:-==--=:.                                                  
                                          ..-=++*:-=--:..= :.       ..:--=*-=-:.                                                  
                                           .:-*#*--===-=:-*-=+   ..-----+++:-:.                                                   
                                          ..:--+*=::-*:---*+--: .--:--:*##::                                                      
                                            ..:.--:=+.*+=:--:-++:..=--*##-:.                                                      
                                              ..:.:-=#+::-+*#:=##==--#%=-.                                                        
                                                 .::..:--=###*=*%%#*=+=-:.                                                        
                                                ...:::::*=-==*+-+=++--:...                                                        
                                                 ....:::::.-+=#+*+-:....                                                          
                                                    .:--:-:==*#=:.-                                                               
                                                    ..:-=-=+*+--:..                                                               
                                                   ...::-=*#+===::..                                                              
                                                     ...:=*%==--:..                                                               
                                                     .::=*#*-=--:.                                                                
                                                     ...-+##--:..                                                                 
                                                       .:-*#+:::.                                                                 
                                                       ..--#%=:.                                                                  
                                                        ..:=*%=.::                                                                
                                                       ...::=##=:...                                                              
                                                      .....::-*%-....                                                             
                                                     ......:-.-##-::...                                                           
                                                      ....:---::##-::..                                                           
                                                        .::::-::=%+--:.                                                           
                                                         .::::::-+#-:...                                                          
                                                         .:::..--*+:....                                                          
                                                        . .::::=+*::...                                                           
                                                      .:.::----=**:.                                                              
                                                     ..:-------++::                                                               
                                                      .::::::-=+-::.                                                                  
                """;
        FRAME[32] = """
                                                           ....     ..::::::::..                                                              
                                                .:::::::::-:-=----::..                                                            
                                                -::------=====*=++=+=-::::.   ..                                                  
                                           .--*=---=====*#*=#*+==+*-:++==-::...                                                   
                                             :..:--=#%*###-=--:---==-=+=-*=--::                                                   
                                            ..:::-#*=*+--:.:.....  .:.:-==:+:::.                                                  
                                           ..-::+*-*-::-:...         .:::==:=--.                                                  
                                         ....::-*:=++:.:....       ....---+:==-..                                                 
                                          .:-:===:-=::..   :       ....:-==--=:.                                                  
                                          ..-=++*:-=--:..= :.       ..:--=*-=-:.                                                  
                                           .:-*#*--===-=:-*-=+   ..-----+++:-:.                                                   
                                          ..:--+*=::-*:---*+--: .--:--:*##::                                                      
                                            ..:.--:=+.*+=:--:-++:..=--*##-:.                                                      
                                              ..:.:-=#+::-+*#:=##==--#%=-.                                                        
                                                 .::..:--=###*=*%%#*=+=-:.                                                        
                                                ...:::::*=-==*+-+=++--:...                                                        
                                                 ....:::::.-+=#+*+-:....                                                          
                                                    .:--:-:==*#=:.-                                                               
                                                    ..:-=-=+*+--:..                                                               
                                                   ...::-=*#+===::..                                                              
                                                     ...:=*%==--:..                                                               
                                                     .::=*#*-=--:.                                                                
                                                     ...-+##--:..                                                                 
                                                       .:-*#+:::.                                                                 
                                                       ..--#%=:.                                                                  
                                                        ..:=*%=.::                                                                
                                                       ...::=##=:...                                                              
                                                      .....::-*%-....                                                             
                                                     ......:-.-##-::...                                                           
                                                      ....:---::##-::..                                                           
                                                        .::::-::=%+--:.                                                           
                                                         .::::::-+#-:...                                                          
                                                         .:::..--*+:....                                                          
                                                        . .::::=+*::...                                                           
                                                      .:.::----=**:.                                                              
                                                     ..:-------++::                                                               
                                                      .::::::-=+-::.                                                                  
                """;
        FRAME[33] = """
                                                          ......   ....:--::::..                                                              
                                              ..:-::::::=--:----==-....                                                           
                                              :--::-----======+*++=+----::-. ..                                                   
                                            .------=--=+*%*=#*+=-+=-:+*==---::.                                                   
                                           -:.-:---=#%*#%#-=---::-=--==:=*+:::.                                                   
                                       .=+*...:::-##=*+--:........:::::--=:=--.                                                   
                                        :-..:--:+#-+=-:::....      ..::::====+=.                                                  
                                        . .:::--*-=-=:::.. ::.      ...:::+-**=:                                                  
                                         ..:-:--=::.=:.:....     : ....:-==-+*-:.                                                 
                                           .-+=++:--:::::.. .:.  -...:---=+-=--:.                                                 
                                           .:-***===---:=-:.  =:==+::---+*+-:::.                                                  
                                           .:--=##--::-*=+-*++=*====---+%*::.                                                     
                                           .:--:-=*%#-:.:=#=.+-:=-----*##-:.                                                      
                                            ...::::-=#*+-##+-:-=:==-*+==-:.                                                       
                                               ...:::::.:=++==+*+**+#*%*+=*.                                                      
                                                .....:::::-++-=#=-:#.=---.:                                                       
                                                ..::..:::::---*-**==%**=+.                                                        
                                                    .::::::-=**--..    =-                                                         
                                                    ..::---+#===::.                                                               
                                                    ..::--+#==+=-:..                                                              
                                                    ... -+#%=---:..                                                               
                                                    ..::=+#*=-:::::.                                                              
                                                      ..-+##==::.                                                                 
                                                        :-*#+--.                                                                  
                                                         :-#%=::                                                                  
                                                         .:=*%=.:.                                                                
                                                        ...:-##=:...                                                              
                                                    .   .  ::-*%=:...                                                             
                                                     . ...:--.-#%-.....                                                           
                                                     . .::----::##-:::..                                                          
                                                       .:::----.-#:--:. .                                                         
                                                       ...:::::--*--::.                                                           
                                                         ..:::.:-*::...                                                           
                                                     ......:::.-++:  ...                                                          
                                                    ....::::----*+-.                                                              
                                                     .:.:::----+=:..                                                              
                                                      .....:---=:-.....                                                               
                """;
        FRAME[34] = """
                                                           ...:.  ...::::::---.                                                               
                                             ..:-::::::--::-:---=*:...    ...                                                     
                                             .:---------======+*++==--::-:-....                                                   
                                            ..::---=-=-+*%*+##+==++-:=*=-:-:::                                                    
                                          .-:.::-::-#%*###-=------=-===::*=-::                                                    
                                         ---:--::-#*=#+=-:...:..::::::::=+=+:-:                                                   
                                      .:=:.:-=::+#-+=-:::.....     ..::.:==:=*+:                                                  
                                      ++-..:-::=*:=-:::::.. .  ..   ...:::*:-*=:.                                                 
                                     .:... .=:-=-:---::....        ....:-==--==:.                                                 
                                          ..-+=+*:---:::::.    ... .:---==*----:.                                                 
                                           .:=*#*===--:::::- .:::---:==-+*+-::::                                                  
                                          ..---=##=-::=:--***-=-+-+=:-+=#*::  .                                                   
                                          ..-=-:-=#%#+=-::::*== :--=-+==*-:..                                                     
                                           ..::-:::-=##*##*--+:-**+.:-+-++:..                                                     
                                                ..::::-:-=++++**+=-:*#+*+*+-:=.                                                   
                                                ......:::-===--+==-*#*+-.--=:::                                                   
                                                ......::.:-=:-++*----:**%#=+:                                                     
                                                   ....::::--+*--:....   .-+                                                      
                                                    ..::---=*-===:..                                                              
                                                    ..:---+#==+=-:..                                                              
                                                    .:..-+*#+---:...                                                              
                                                     ..:-*#*-=:::::.                                                              
                                                      ..-+##-=:..                                                                 
                                                        :-*#+--:                                                                  
                                                         :-#%=-:.                                                                 
                                                         .:=*%-:..                                                                
                                                          .:=##-::..                                                              
                                                       ..  ::-*%=:                                                                
                                                       . .:--:-##:......                                                          
                                                       .:=-===::##:.::..                                                          
                                                      .:::-----:-#-:-:.                                                           
                                                       .....:::::+-:::.                                                           
                                                         ...::.:-*-:...                                                           
                                                    ... .....:.:++:.. .                                                           
                                                    ...::::::---+-...                                                             
                                                    ....:::----=--...                                                             
                                                         ..:.-=*#::....                                                               
                """;
        FRAME[35] = """
                                                          ....:.  ..::::::----..                                                              
                                             ..::::::..:--:-----=*:...   ....                                                     
                                            .:----------====-+++++==+=---::. .                                                    
                                            ..:::---=--###*=##*+==+=:*#==::::.                                                    
                                           ...::-:--**####+-==--:::--==*=#+::.                                                    
                                         ....::::=#*=#*=-:....:::::::::=+--+:-:                                                   
                                       .-=:---:-+#-+=-:::...     .....-::=+-+++-                                                  
                                       =:.-=*--=*:+-:::::.. .       ...::-*:**=:.                                                 
                                     :-=...:.--+=----:::...      . ....:-==:-=--.                                                 
                                   .::-+ ...-++*+:=--:::.::       :.:---==*:=--:..                                                
                                     :=+    :=#**==--:-::::.:*:---::====++*::.::.                                                 
                                          ..:--+#*+-::-------**+--+--====*::  ..                                                  
                                          .:-=:.-*%##*+--:::::+.++ :=-=*-==.  .                                                   
                                           .:---:.:-+*###*#**=:.:-++++:-:-===  .                                                  
                                                ..:..::--=++=***##*-:-=+++=+#-..:                                                 
                                               ......::::--==----::--:#%%*:-==#+*-                                                
                                                ........:-====-##=-::::*=**#**:                                                   
                                                   ....:::---#*=::.....:   .-=-                                                   
                                                    ...:--=+*-===-..                                                              
                                                     ::--==%+==+=:..                                                              
                                                    ...::+%#=-:::...                                                              
                                                    ...:-*#*-=:::::..                                                             
                                                      ..-+##-=::.                                                                 
                                                        :=*#+--:                                                                  
                                                         :-#%=--.                                                                 
                                                         ..-*%=...                                                                
                                                          .::##-:::.                                                              
                                                    .  .   ..-#%-:..                                                              
                                                       ....-=--*#:......                                                          
                                                       .-=====:-+*:.:...                                                          
                                                     .::::-:---:-#--:::.                                                          
                                                      ......::::-*:-:::.                                                          
                                                    . . ......::-*:::..                                                           
                                                    .........:.:+==..  .                                                          
                                                    .......::---*::..                                                             
                                                    ....::::::-*+#:..                                                             
                                                         .::.---==.....                                                               
                """;
        FRAME[36] = """
                                                         .....:...:::::::::::-.       ..                                                      
                                            .:::::::..:::-::----++:...   .:..                                                     
                                            .::::---==---=--=++++++---::-::...                                                    
                                             .:::-==---*##*+##++==+=:=##=::::                                                     
                                            ..::-:--*#*##*+-=-::::--=++-.*=:.                                                     
                                        ..:...:::-*#=#+=-::..-:::::::.:-==+=:-:                                                   
                                       ...:::-::+*===-:::::..   ......:.:=+--++-                                                  
                                      ....::-:-+*:=-::::..:.        ...::-*:-+=-.                                                 
                                      ... .::-=+=:==-:...::        ...:.=-+::--::                                                 
                                       :-----=+++:=--::...:......---.:--==#:---:...                                               
                                       +:.:-++##*===::-:::::::::::+-=+=:+++:+:.....                                               
                                       *. .::--+#*+-::-----=------==-===%+:-=+=.                                                  
                                      -*=.:----::=%#%*+=--:-:--=--#-=**+=+-%::*  .                                                
                                     =.+-+.:----:::=+*#%##*##**==-:.::*+:=---=-+.   .                                             
                                       -:==    .......::-===+*####++#++-.:-***+=#*                                                
                                               ......::::---=--::-:-::::=+=*##+=*%#=-*                                            
                                               .........:--=--=#*---:::--++:=++::-.. .                                            
                                                     ...::-:==+:-::.....    ::==+++.                                              
                                                     ...:-==#==+---.             .:                                               
                                                    .::--==#=====-..                                                              
                                                    .::--=#++---:....                                                             
                                                    ...:-##*-=-::.:..                                                             
                                                      ..-*##-=--.                                                                 
                                                       .:-*#+---.                                                                 
                                                         :=#%=:-..                                                                
                                                         ..-#%-....                                                               
                                                          .:-##-....                                                              
                                                           ::-*#:...                                                              
                                                        ...:==-**:......                                                          
                                                      .:--=+=-::*+:.....                                                          
                                                    .::::::------*:::::..                                                         
                                                    .......::.:::=:::::..                                                         
                                                       .......::=+:::...                                                          
                                                    ..........:-+=%..                                                             
                                                   . .......:.--:==:.                                                             
                                                     ...::.:-:==:-:..                                                             
                                                         .:--:+#-:......                                                              
                """;
        FRAME[37] = """
                                                        ..........:::::::::::-        .                                                       
                                           .::::::::...:::-----=++:..    .:                                                       
                                           ...:::---+=---==-=++*+++--=:::::..                                                     
                                             ..::-+=---*###+##+===+=-=##=:::.                                                     
                                           ....:::-=*#*#%**-=-::-:::=++=:#=:.                                                     
                                        .:....:::-*#+#+=--::::-::::::.:-+-+=--.                                                   
                                      ...::::-:-+#-==-:-:....   ......:::=*--=+-.                                                 
                                      ....::-:-+*-=--:::....       ....::-*:===-.                                                 
                                      .....-:-=+=:-=-:.....      .:.:...--*----::.                                                
                                        ...:-=+*+:=-:::....::......:::--==*---:::.                                                
                                           .:+##*-==---:..::::-::::--=-==*+--:..= ..                                              
                                        .:---:-+#**-::-=-------=------*=#*::.*-.-=                                                
                                       .:=#=+*##==%##+++:::-:=-+-+-+-=+==.#+#++=.-+:                                              
                                         ==:---=-::=+#%%##***#**++=-::.:==*::*-*+-=.                                              
                                          -:   ..:-:.:::-==++*#*#####*=++*+:.----==+=+  -                                         
                                          -*+  .. ....::::----:-::::::--+=+--#*-+***#=. :                                         
                                          .+===+: .....::-----=#*---:::--:...==:.-.::*#++.                                        
                                           .  -.-.  ...:::--=++:-::.....    ..:.=+**==.                                           
                                                    ...::-==#==+=--:.               +:                                            
                                                    ..:--==#=+=-=-...                                                             
                                                    ...==+%+=--::.. .                                                             
                                                    ....-##*-=-:......                                                            
                                                      ..-*##===-.                                                                 
                                                        .-+#+---.                                                                 
                                                         .-#%=::..                                                                
                                                          :-##-.....                                                              
                                                          .:-##:.....                                                             
                                                           ::-**:...                                                              
                                                       ....:==-#+.. ..:.                                                          
                                                     .:::-=+=--:*-:.....                                                          
                                                   ..:::::------=+::::.:.                                                         
                                                   .... ..:.:..:+#*::::..                                                         
                                                       .  ...:----:+::..                                                          
                                                    .. ......:.=::::..                                                            
                                                    .........:-=*=:..                                                             
                                                     . ....:-:-+=:::..                                                            
                                                          :----=-:... ..                                                              
                """;
        FRAME[38] = """
                                                          ......  :...:::::::-       ..                                                       
                                           .:::::::::.::::----=+++:...  ..                                                        
                                           ....:::--+=-=-==-==+++=-++-:.::..                                                      
                                             ..::-==-==+*%%+***++++-=#*--::.                                                      
                                         .. ...:::--*%**##=-:::-:-+-:=+:-#-..                                                     
                                        .:...:-::=##=*+--::::--::::::..:-+#=:-.                                                   
                                      ..::.::-:-+#+++-::-:::.  .......:::-----=-.                                                 
                                     ....:::-::=#:---::.....     .:::.::::+:==--:                                                 
                                        ..:-::==#:==-::....        .-::---+---:::.                                                
                                        ...:-+++*:---:.....:.......:--=--=*:=-:::::                                               
                                        ....--***=-=--:...:-::::::-==*+:++=:-::...  .   .                                         
                                        ..::---*##+=::--------------==:+#*::=..... -.  ..                                         
                                       .--::--::=*##+**=--:----+-+++=-=#=--+#++==-  -=:-:                                         
                                        .::----:..:=+###%******++*++==----:-::=-=:-+=#-:*=                                        
                                          ......:.:::=*#==++**##%#%##%##*##+=:-===+=---+--                                        
                                               ...-=*#=--:---::::-:-:---+=+=*+#**-.:=#*+:=+  :                                    
                                               . .=*+..:::----=#---------:::::::::.:*%#=*-%*  .                                   
                                                   ..=::-----++:---:.....  .......--::::.*%* .                                    
                                                    .-++==-=+-+====-:              .:=+*=..++=                                    
                                                     .::-=+*=+=---:.                  .-:                                         
                                                    ..:::=#*+--:..                                                                
                                                     ..:-+#*-=--......                                                            
                                                      .::=#*-===:                                                                 
                                                       .:-*#----.                                                                 
                                                        .::##-::..                                                                
                                                          :-#+:  .  .                                                             
                                                          :--#+......                                                             
                                                           :-:*-....                                                              
                                                        ...:=--#=..   ...                                                         
                                                    .::-------:+:=+.....                                                          
                                                   .:::::---::--::::::..:.                                                        
                                                   ...  ..::::::-+:::::...                                                        
                                                    ..   .....::=--:::..                                                          
                                                         ....::=--::.                                                             
                                                         ....:----:..                                                             
                                                         ..::::::::...                                                            
                                                          ::-::::....                                                                 
                """;
        FRAME[39] = """
                                                       ........  ......:::::-:       .                                                        
                                          ..::::::::::::::-=--=+*+:...  ..                                                        
                                           ....::-==+=-==-======+=+=--::.:.                                                       
                                              ..::===-=*##%#+=++==-+#%##=*+                                                       
                                         ..  ...::-==%%+++=-:-:..:.:::-:##=.                                                      
                                       .::...:-::=*%*++---:::::::::=-..:-:%#=:                                                    
                                      ..::.:--:-+%*==-::--:.......::::::.--*----:                                                 
                                      ....::=:-+#+---::.....       .::::::-=+=--:.                                                
                                       ..::-:-=*#----:......       ...::--==+-:::.                                                
                                        ...:=-+**-:--:....::.......:-===-=+-+=:::.                                                
                                        ...::++#*+---::...--:::-:--==++-==:---:...     .                                          
                                       ...::---*#*+=----====-----===++:-#+=:.......                                               
                                      .-:::---::-##%*++*+===--=-+=#++++*==--:..:++..     .   :.                                   
                                       .::::::--:::=*#%##%#*+*++++++=====-===-=--==+=#*+ : ..#:.                                  
                                          ....:::..::::-==+*##*######%#%#**#+*=--::::*=-++*=-+.                                   
                                              .......:::::-#+-:::::-----=+***#**#*++=..:=-:-*.*:                                  
                                               .  .....::=%=.--%+=-----=:::::-:-::.--*+-:.*+-::                                   
                                                   ....:::*--===--:::............. ....==#*:-#=.                                  
                                                   ....::--=#=+==-=-:                 ..-=::+-+=:                                 
                                                      .::-**=+=---:..                  :-*..-#.                                   
                                                      :::*%*--::..                      :=:  #*                                   
                                                     ...-+#+-=--:. ...                                                            
                                                     .:::=*+-===-                                                                 
                                                       ::-=+----.                                                                 
                                                        .::+=:::.                                                                 
                                                          --#-:.    .                                                             
                                                          -+-*-.. ..                                                              
                                                          .:-=-=-...   .                                                          
                                                          .:-::.-:.   ...                                                         
                                                   .:::------.::.:... ..                                                          
                                                   :::::-==-::::-=::.:....                                                        
                                                  ...   ..:::::--:...::...                                                        
                                                    ..   . ...:-+-:::::.                                                          
                                                         ....:-----:..                                                            
                                                           ...:-:::...                                                            
                                                          ..:::::::...                                                            
                                                         .::::::.....                                                                 
                """;
        FRAME[40] = """
                                                       . .... .. .....::::--=::++:                                                            
                                         ...:..:::::-:::::-=====*+-:...-=:                                                        
                                          . ....:-====-=-====****++-=-*%=.                                                        
                                             ....:----=#%%%%%#*+*+**#%%#+:.                                                       
                                        ....  .::--+#%%%#+=:--:-:...::=##%+:.                                                     
                                       .::...:::-+%%%*+=+-::-::::::::-..-*%+-.                                                    
                                      ..:.::-::-*%%*=-:-==-.........::--::*%=---:.                                                
                                     ....:::-:-*%#==-::......      ..:::-::#*=--:.                                                
                                       ..::--:=+#+-=-:........     ..:::---**=:::.                                                
                                          .-===*+----:....::.::...:-=++=--:#+*-...                                                
                                         ..:==*++=--=-:..:--:::---====+-==+*--:...                                                
                                     .::.::::-=###==***==+++-=---===**-:#+#-.........                                             
                                    .::::::-:-::-*#%*---+****#***#*###=#**-=-:.....                                               
                                     ....:::::-::::+##%####*+====--==+*+*****#+#-*:=.:..=:.                                       
                                          ....::::.::::==*+#**#*%####%##**+***-=++***#=*:=== ..                                   
                                              ... ..:.:::::-::.:::-*#--=*+*#**#*##%#*-==#-:=+*=+    :                             
                                                   ....::::-----==-+=--=-:-::::.-::=-#%##=.:+.+++:=.                              
                                                    ...:::--::=+==:+-... .............:-+*%+:::+=+- .-                            
                                                    ....:---:++=---:+#+         ..     ..:-+==:.#--#.:-:                          
                                                      :.:-==+++-:::..+:                 .:..--=:==*+:                             
                                                      .:.=#+-:-:..                       :==:-::-=:=:                             
                                                    ..:.:=*=-----.                       .=+-==+#=                                
                                                     ..::-+-==-=-.                       -*=.:.--                                 
                                                      .:::--==--:.                      .:-..-:                                   
                                                       .::::+:::.                          -=#.                                   
                                                         .:..:-      ..                                                           
                                                         .--.:-.  .                                                               
                                                          :--::::...                                                              
                                                   .      .:--:.::      .                                                         
                                                   ::::::::----:::...  .                                                          
                                                  ..::.:----::::-::........                                                       
                                                 ....   ..::.:::-:.........                                                       
                                                   ..   .  ..:-++-:....:.                                                         
                                                          ...:::::::..                                                            
                                                            ..::::::.                                                             
                                                           .:::::.....                                                            
                                                      .. ....:... ....                                                                
                """;
        FRAME[41] = """
                                                      . ....    ...::..:::--=-*%-.                                                            
                                          ......:-:-::::::=======*%=:....                                                         
                                             ....:-====-===+**#***#+=--::..                                                       
                                             ....:---=+%%%%#**++=+++*#%#+:.                                                       
                                         ......-:--*%%%*%*+*-=+-:=::==+*#%*:                                                      
                                      ..:...::::-+%%##***----:::::---=::=+%*-..                                                   
                                      ..:::--::=#%#+=---=--:...  ...::--:-=%=-:::..                                               
                                      ..::::::-+%*+=-:.......    . ..::--:=##=--::                                                
                                        .::::-=+*===-:........    ...:::---*#=::..                                                
                                          .--=+++=*=::..:::.:::...:-====--+#**=:..                                                
                                         ..:=+**+=-+=:....:-:::---=+-=++==*#--:.. .                                               
                                    .::...::--=%##+-+=+#=**+=----==+**=:*##-:........                                             
                                   .::....::-:::=%##+=-=-++#+%+%*####-++%*+:-:......                                              
                                    .....:::::-:.:-=#%#%%***=+===--=-++#+#*##**=*-#::.....                                        
                                            ..::::...--=++#**##%#%#%#%##*++*+==--+-#*#*=+:...                                     
                                              ........::::::.:::::-:::::+**#*##%###*#*=+#*%=:+: .                                 
                                                   ....::::---:=%*==+-:-#=::::.-::--+#%#%**%%:+=+                                 
                                                    ....::-=:-+==-----=.:=-........::.:--*%#*%#=--:                               
                                                      ..:-=--*+=---:..-++=-==:  ..     ...-*#*-==+:.                              
                                                      ..:=-+*+=--::... ..:-              ..:=+*:=:*.    .  -                      
                                                     ....-+*::-::                        .:.-=*:=:+ ::..                          
                                                    ..:.:--------.                      .:.::-=-==** .                            
                                                     .:.:--=:=---:                      .:.-=.::::=: ::--                         
                                                      ::::.::=--:.                      .-::-::.:==-:                             
                                                       ::::::-:.                      -.=:-+*=#%+.  .                             
                                                         .--.::       .                 :.-=-.:.                                  
                                                         .--=::.  .                     -++.                                      
                                                         .:::--:...                         .  .                                  
                                                  .. ..  ..:--::...                                                               
                                                  .:::::::::--::::.:.. .                                                          
                                                 ......:---:::::-:...... ..                                                       
                                                ....   ..::.:.::-:..........                                                      
                                                         ...::-+-::.....:.                                                        
                                                          . .::::::...                                                            
                                                             .:::....                                                             
                                                           ..:::......                                                            
                                                     ... ....:..   . .                                                                
                """;
        FRAME[42] = """
                                                       ..   ...-+-..::=*-:--==::...                                                           
                                          ......:--:-=:::=#*====++=--:...                                                         
                                             ...:-==-+====+*#%####**#*+-:.                                                        
                                            .....:-==*#%%%%**++===+=+*###=.                                                       
                                         ......-:-=#%%#*=*#***+-+**#*-:-*%#-.                                                     
                                      ...:...:::=#%#*+++++--:::.:-=-==+*:=*%-..                                                   
                                       .:::--:-=#%*++-=+=---:..  ...::--+--##-:::..                                               
                                      ..:::-:--#*++==::.::..     ......:+=:+%=-:::.                                               
                                       ..:::-==++==+::........     ...:---:*#*-:..                                                
                                         ..--=+++++-:........:...::--==+===****=: .                                               
                                        ...:==**+==-::....:-:::::-==-=+#=-+#=-:.                                                  
                                   :::....:---=##*+--------+=-----=***#==##+:::.....                                              
                                  .:....:::-=-::=*%#++=:--+-=-=-++*#%#-*#%+--:.......                                             
                                   .......:::--:..:++##%%#*+*+++=-==---=-=-----::::....  .                                        
                                             .::::..::--=+*+#*##%#%#%%%####**+==-=+---:::::..                                     
                                                   ...::::::::::::---===+=*+**+--++*%*+-----:..                                   
                                                   ....::::--=+-**+=-:--:::::-:-::=-=#*%#*+---:.                                  
                                                    . ...:-=+=*+==---::......:--=-:.+*::=+*#*+-:..                                
                                                      ..:--*%%*=-:::........ -+....    ...-=*%+-..                                
                                                      ..:=+%-*---:..          :+=-       .::-*%+::.                               
                                                     . ..-*=%::::.              =:       ..::-*#*:.                               
                                                    ...:::+==-:--.                        .::=***=.:                              
                                                     ..:::-*==---:.                       .::=++%**:.                             
                                                      :::--=++--:.                      ...--==*=*=.                              
                                                       . --::::                        :-.====+::#                                
                                                         ::::::        .             .=+.=====*+*                                 
                                                         .:---:.                 :-:=-----::-*==++=                               
                                                  .      :::--:...               ::::::: :=:--++:   .    ..                       
                                                  .. .   .::::::..               .:--:-..--::::+#:                                
                                                  :..:::::::-:::::...             ::                                              
                                                .......::::.:::::.   .. .. .        .                                             
                                                 ..    ..:..::::=::.........                                                      
                                                         ....:-=::.......:.                                                       
                                                            ..:::.:....                                                           
                                                             ...:.....                                                            
                                                            .:::.....                                                             
                                                      .  . .....    ..                                                               
                """;
        FRAME[43] = """
                                                            ..-=....::::-:---=-:::.                                                           
                                             ..=:-*-=++*--++===-===--:.:..                                                        
                                          -:=-=+*=-=-==#=+*#####*##*#*+=:.                                                        
                                            .....----=+%%##*=+=-=-==+*##%+:                                                       
                                           ....-:=+#%%*==*%%#+#+*%###::-=%%=.                                                     
                                       .....::::=*%#++*%*+=::.:.:=+-=**+.-#%+...                                                  
                                       .:::--::=#%++**-=----:... ..:::-+*.:*%=:::..                                               
                                      .::::-::==*=++:::::::..     ...:.:+=-+#+-:::.                                               
                                        :::::-=+=+=--:........     ..-::---=#+=:..                                                
                                         ..-==+++==-:............::---==+==+*+*+:                                                 
                                       ....:==#*++=-::....---::::--===+#=:=+=-:.                                                  
                                  .......:-==-=#*#=--::::--++--=-=+**##+=+*=::::.                                                 
                                 ........::-==:::**%#===---+++==+*###%+-#%+-:::......                                             
                                 ...........:---:::=+*##%%###+*++===--=-=====---:::..   ...                                       
                                             ..::-:..:::-==++***####%%%#%##*#**==-=---:=::::.                                     
                                                   ...:::::::::::::-:--=++*=*+*###*+-:.:+=:-:...                                  
                                                   .....::::--=*%**=-----:::-::::::---##=-----..                                  
                                                      ...:-=+=+*+----::.....:::.....::..-*###*::..                                
                                                      ..:-==:+=:::::.   .. . ......=+-..:.:=*%#::.                                
                                                      ..:+=#*-==-:.               -=     .::=***:..                               
                                                        .:-#-:.::.                  =..  ..::**#-:.                               
                                                      ...:--:-:-:.                 :      .::-+#=:.                               
                                                     ..:.:-==+---:.                       .:::=*-:.                               
                                                      :::---+*=-:..                     ..:-:=+=:.                                
                                                      .. --:::.                     .   .:-=-==-:.                                
                                                         ::-:::.                    .::..---==#==.                                
                                                         .:----                    .::-==-===*#==:                                
                                                         :::-:..                ::.:=+===+---#.                                   
                                                 .. .     :::::: .         .*=----=-+:-##+*-                                      
                                                 ....:::::::-::.:..         -##:-=:::-----:.:                                     
                                               ... ....::::.......   ... .   :          .:       :.                               
                                                       ..:..::::-::. .. .... .                    .                               
                                                         ...::-:....... ...     ..  ..                                            
                                                            ...::.....                                                            
                                                             ...:....                                                             
                                                            .....   .                                                             
                                                          .......                                                                     
                """;
        FRAME[44] = """
                                                            .....:.:.:::-:-:--::::.                                                           
                                              . .:-------=*=======+=-::..                                                         
                                             ...:::-=====*=#%###*#*##*+=:..                                                       
                                           .....:----*#%%%**=+:---:-=++%*#:.                                                      
                                        :===...:--+#%%#=--+-*---++===:.:+*%*.                                                     
                                     .:-+-:=--:.-=+==-==+=-:...:-=-:-:=:.:+%*...                                                  
                                     ::-:::-*-:-+=++-+=---:::.....::::::-::+#-:::..                                               
                                      .::::--:-=*+=-::.:-::..     .....:-::-*+-::..                                               
                                        ...:--==++=-:........     ..--------#**:..                                                
                                          :--==++++-:......:.....::-====-:-=*+++-.                                                
                                    ... .:--==+**+=-:.....---------=++*#+:=*=-:.                                                  
                                  ...  .::===---+%+==------===-==****###==*=:::..                                                 
                                  .......::-==-::--#%*+++:--==-==*%#%#%*+#+--::.. ....                                            
                                 ............:-=:.:-=++##%%#%##**+**+=-=---=-=-::-::.    ...                                      
                                              .:--:....:::-=+++*++#####%#%#%####+++==--:::::.                                     
                                                .......:::--:::.::::::::--=-=-++#*#####*+==-:...                                  
                                                    ...:.::::-+#=::-==*--=--:::.::.:-:=*#%%#=-:. -:                               
                                                      ...:--+#+----:-::.....:::........::=+*#*-:-:==                              
                                                        .--=*=----::.      ......... ....::--=%----#-                             
                                                        :==+=-::::.               ...    ..-:-**+:...--.                          
                                                        .:-:-:....                     .==::.--##:. ::=                           
                                                      ...::::-:::.                      .=+-::-**:.. -                            
                                                     ..:.:---=---:.                       .:::-+*:.                               
                                                      ..:-=--+*-:..                      ..---=++.                                
                                                      ...:-::..                         ..:==-+=:.                                
                                                         ..::.:.                      .:.--===+-.                                 
                                                         .:-::..               ..  :.::-==+=+=..                                  
                                                         ..:::..      .      .:-:=-:---=+=-==.                                    
                                                 .. .    .:.:::.    -.-=----::.==---=---=+...                                     
                                                ....:....::::::.. --+---==-----------=++...                                       
                                                   ....::::....... ==-:---:-:=--+=                                                
                                                       .....:::::...  ....:-:..:=.                                                
                                                         . .:::........ ....   .::                                                
                                                          .........                  .                                            
                                                             ..... ..                                                             
                                                             ....                                                                 
                                                           ......                                                                     
                """;
        FRAME[45] = """
                                                            .:::.::::::--:----::::.                                                           
                                               ..:-------=+====-+=+=-::..                                                         
                                              ..:::-==+==+*#%###*#***#*=:..                                                       
                                          ......:-=--+#%%%#*=+--=-:-=+*##+:.                                                      
                                         .. ..:-::-*%%#--=+++---+---....+*%+.                                                     
                                       ..:::-+-:-+%%*=+*===::..:---:::::.:*%+:..                                                  
                                      ..:::-=-:-+##=-++==-:::.....-::::-:.:=#-::...                                               
                                      .::=::=:-=*#+==-.:--:.      ....:::---++-::..                                               
                                        :-=--:-==:++-... ....     .::-::--.-*##=::                                                
                                        +**+#=*++*==:.....::.....::-==----:-++=+-:.                                               
                                   ... ..-==:-=**+=-:.....:-----=--++**#*:-=--:..                                                 
                                  ..  .::-------+##*=------===-=+****#*=-=%-:::..                                                 
                                   .......::==--:-=*%%#*+=-----===+*#*=:-%=---:..  ...                                            
                                 ......     .:-=-:..--+**###%#%###+#+*++==-===-=--==:. ..::.                                      
                                              .:-::::::..:-----==+=***##*#*%#%##***+==--::::..                                    
                                                .......::-=---==::::::-::::----==+#*##%+*+==:...                                  
                                                     ....::::-=+***+=--:---=+-:::::..::++*%%*=:..                                 
                                                      ...:--=**+=-:::::....:-::.......:.::-+%%*-...                               
                                                        .--=*=-:--::..     ........  ......:=*%#-...                              
                                                       .:--==-:.:..              ..... ...::-==%*:.                               
                                                         .:::::...                      ...::==+%=.. :                            
                                                      ....-::::::.                        .--*==*=+==+-                           
                                                     .:.::--:=---:.                       .:-+=+==-:  =                           
                                                      ..:---:-+=:..                      ..:-*===:   .-+.                         
                                                      ....::::.                         ..:==+==+.  .=:- .                        
                                                         ..:....     .     .          ..:-=+==+=.     ..                          
                                                         .::....::       .= :  -.   .::--====+-.                                  
                                                         ..::-:+:--::-::::==:=-:-:::----====-.                                    
                                                .. ..    -.::*: ..--.-:.::....-===---==-:-*:                                      
                                                   ........:.::+--=..:-=-==--------:---::..                                       
                                                  .  ..:::::.....:--::::-:-::-.:+=:-.                                             
                                                        ......:.....:::::+..::                                                    
                                                        .. ..:.......                                                             
                                                          .........                                                               
                                                              ....                                                                
                                                             ...                                                                  
                                                           . ....                                                                     
                """;
        FRAME[46] = """
                                                           .:::..:::::----:--=-:..                                                            
                                                ..:------====--=+++=-::..                                                         
                                              ..::-=++=====*#*##*#****=-:::.                                                      
                                           ....:--==-==#%%##**-+--===*%#*--:.                                                     
                                         ..:::-++:-=%%#+-=+##=--+----:::*##*=.                                                    
                                       ..::-=++::=#%#==+==--:..::-:.::-::-***+.                                                   
                                       .:::--=-:=%#=-+=*=-::::..::-:::::-:.++-::...                                               
                                      ....:-+=:-*#+-:..:::::.     .:...-:-=*%*=:...                                               
                                        ::::=--==+==-:...:.-      .::----:==.+*#-..                                               
                                         .:-===*++==-..--+*-:...:::-==-===::::=*-:..                                              
                                    .  .::--+==+#*==-:==.::-----=-=+++***=::::=....                                               
                                     ...:------:-##*=-=*---====+++****+*+---:::.                                                  
                                  .........::===-::++**%#=+===----==+***=-+=-::..:-...  .                                         
                                             .-=--=--:-:=+=*#%%#%#%#%**++=+=+==-=-++=:...::.                                      
                                              .::::::--:::::::---=-=++**#*%##*%#%##**+----:::.                                    
                                                .::.....:----=+****=-::::::::-:--+++*%###*+=-:..                                  
                                                      ...::::-==+*===-::---=+--::::.::-==*#%%*-..                                 
                                                      ...:-::+++-:::::....::=-:...::.:::::-=#%#+...                               
                                                        .:--=--:--::..    ..   ...... ...:..-**#*:..                              
                                                        .-----:.....                    ..-..:=*%=...                             
                                                         .:::::..                       ...-:-:+*+.                               
                                                       ...::.::::.                        :-::-=+#.. .                            
                                                     ...::-::=--::.                       .--:--**.                               
                                                      ..:---::=+-.:                      .:::-=+*-..                              
                                                      ...=-++.*--: .  ..                .::-+-=*+:                                
                                                        ..:==#+-+=:*--.-.            ...:::-++++:                                 
                                                         .:-:---:-:---:.:.-        .::::=:-+=*+:                                  
                                                        ...::--::::-: .:=+==.:::-----=--*==-++*#**+                               
                                                  ..     ::::-=.-=---:-:=-=----------*==---#.    --                               
                                                  ..........::....-------::::::----==-==+:.     :-.                               
                                                    ...::::..........::...:::::::--=+=        ---=                                
                                                       ......:........     .....              -:..                                
                                                        ..........                                                                
                                                          .........                                                               
                                                              ..                                                                  
                                                              .                                                                   
                                                              . .                                                                     
                """;
        FRAME[47] = """
                                                          ......::::::----:-==-:..                                                            
                                               ..::-----=====--=*++==-:..                                                         
                                              ..::-=+++====+***++*#**+-::::.                                                      
                                            ...:-=+====#%%%%*++*==*=+#%#=---:                                                     
                                          .:::-=*-:-#%%#==+##=--+-:--=--*+-++-                                                    
                                      . .:--==+=:-###=-===-=:...:-:..:--.:++**-..                                                 
                                      .:.::-=+-:-#%+-+=+=::::...:::::::::..:.-=:..                                                
                                     ....::-*=:-+*+-:..::::..     .:..:::-:..--:..                                                
                                       .:::-+--=+*==-........  =..::::----:-:*+*=..                                               
                                        .:--===*++-+-......:::*#*-:-=-=+***--+--::..                                              
                                     ..::-:-+=-=#*+=-::::::==--==-=+=+++*+=---:.....                                              
                                    ....::-----:-+#*====-==+#+=*+****+=+**=--:..  ..                                              
                                   ...      :-=--.:=+*#%#++++=+--=+-==***+*=-:..----......                                        
                                             .:=-::.::::++-+*-=+++%*##*#***+===---=+*:...::.                                      
                                              .::::::::.::-:=+--:-:-==+=*+*##*#*%%#***==--:-:.                                    
                                                .::....::----=+**++=+-:::::::::::===+####*+==:..                                  
                                                      ...::::-===+==--:::---=-::::.::.:-=+#%%*-...                                
                                                       ..::::===-::::.....:.-::..:.....:.::=+#%*:...                              
                                                        .:----:::::..     ..   .....  ...::.:++##-...                             
                                                        .::::::....                    ...::..=*%*..                              
                                                        .:*+:--.                     .  ..::-::=*#                                
                                                      ...::=+-:-:.                       .-=:::-+#:..                             
                                                     .:==+*-==**+=::                     .-=:::-*+:                               
                                                      .:::==**+=-=--:::.                 .::::-=*=...                             
                                                       ..-:=+:==+=+=-.                ....:-=-=+=-.                               
                                                       ....:--=:-.-=-::              .::::--+=+==. ..                             
                                                         ..::-=:-:--==*-.     .. . .:-::=--+===-.                                 
                                                        . :.::--=--+++-=--::::-----::--=++===-:...                                
                                                  ..    ...:.:.:--=-=-=*=:::.:::=--:--:-*+-=:                                     
                                                  ...   ...:::...:::-=--:-::::-:--==+--=+*.                                       
                                                      ...::...........:.::::::::::::--=*+=+#-                                     
                                                       ...............   ..:...:.  :=.:   =-                                      
                                                        ...... .                       .:=:.                                      
                                                          . ...  .                   -=:.:                                        
                                                              .                       :  .                                        
                                                                                                                                  
                                                                                                                                      
                """;
        FRAME[48] = """
                                                           ......:::::--:--==--:...                                                           
                                              ...::-+=---===---+++===-:....                                                       
                                              ..:---=*###***++***=::-=--:::..                                                     
                                           ...:-==*+*+=###%##++*-.::...:---==-.                                                   
                                          .:::=+**--+#%#==%#*==-=-::::...:-----                                                   
                                        .:--:-*+:-+#%*==---+-:..::...:::-::==---.                                                 
                                      ...::-=+=.-*%*=+==-::::...:::..::::-*==::..                                                 
                                     ....::-*#--*#*--..::.:..    .:-.::.::-==**-..                                                
                                       ..::-+--=++===..... ..   .:::::-=---*###*=:                                                
                                       .:::---=+*+=*-:....:::..::------=+++=+=+-::..                                              
                                     .:::::--=-=*#+=+--::::-----==+=====*+=---:......                                             
                                   ........:---::=#*+=-=====+++++++*=*++**=--:...::-.                                             
                                            .:=--.:--*%##*#*=--=----==#*-==+*-:::-=+=...:..                                       
                                             .:--.:...:-=++***#####*++=+*#*+===---=++-:.::...                                     
                                              .::.:::::.:::-:--:::::.:=*+++***###%##**+-===::.                                    
                                                .... ..::::---==+-=-=:-:::::::::::=-=*##%##+-:.                                   
                                                        .::::-----:--:::::-:---::::...::-=*%%#=...                                
                                                    ..:..:::-==--=-::.....:.:-:.:...::.::.:=*%%#:..                               
                                                  =- ..-+==::=:::::..       .:  ..    ...:..:=*#%- .                              
                                                  ..:::-=-.-=---.                      ..:::::=+%*...                             
                                                   -*-:--=--*+*#::- :                   ..:-:::*+#-. .                            
                                                  :-..:=---=*=:*==:                      .:-:::-+#+..                             
                                                     ..:::===+--+**                       :-::--=*=. .                            
                                                     ..:::::**===+*+:                    .::----++:.                              
                                                       .:..:+*#*++*:.                 ..:::---=+++.  .                            
                                                       .....=+=++*+-:.              .:::::--+=++*: .                              
                                                        ....:===-=*#+-:..    .:..:::--::-=-+====.                                 
                                                         ....:==---+++==--:-------:::---=+==+--...                                
                                                  .     .......=---:-=++#-:=-::--=--:-*+=---=.                                    
                                                   ..  ..........:.-::--:-::::-:--====+=++:..                                     
                                                      ......... ....:-:::::--=++=-:--==.....                                      
                                                       ......           ....---+:....  .                                          
                                                         ..           .:.:+-+  :*.                                                
                                                             .       . --=.:-=:.                                                  
                                                                         . ..                                                     
                                                                                                                                  
                                                                                                                                      
                """;
        FRAME[49] = """
                                                           ......:::::--:--==--:...                                                           
                                              ...::-+=---===---+++===-:....                                                       
                                              ..:---=*###***++***=::-=--:::..                                                     
                                           ...:-==*+*+=###%##++*-.::...:---==-.                                                   
                                          .:::=+**--+#%#==%#*==-=-::::...:-----                                                   
                                        .:--:-*+:-+#%*==---+-:..::...:::-::==---.                                                 
                                      ...::-=+=.-*%*=+==-::::...:::..::::-*==::..                                                 
                                     ....::-*#--*#*--..::.:..    .:-.::.::-==**-..                                                
                                       ..::-+--=++===..... ..   .:::::-=---*###*=:                                                
                                       .:::---=+*+=*-:....:::..::------=+++=+=+-::..                                              
                                     .:::::--=-=*#+=+--::::-----==+=====*+=---:......                                             
                                   ........:---::=#*+=-=====+++++++*=*++**=--:...::-.                                             
                                            .:=--.:--*%##*#*=--=----==#*-==+*-:::-=+=...:..                                       
                                             .:--.:...:-=++***#####*++=+*#*+===---=++-:.::...                                     
                                              .::.:::::.:::-:--:::::.:=*+++***###%##**+-===::.                                    
                                                .... ..::::---==+-=-=:-:::::::::::=-=*##%##+-:.                                   
                                                        .::::-----:--:::::-:---::::...::-=*%%#=...                                
                                                    ..:..:::-==--=-::.....:.:-:.:...::.::.:=*%%#:..                               
                                                  =- ..-+==::=:::::..       .:  ..    ...:..:=*#%- .                              
                                                  ..:::-=-.-=---.                      ..:::::=+%*...                             
                                                   -*-:--=--*+*#::- :                   ..:-:::*+#-. .                            
                                                  :-..:=---=*=:*==:                      .:-:::-+#+..                             
                                                     ..:::===+--+**                       :-::--=*=. .                            
                                                     ..:::::**===+*+:                    .::----++:.                              
                                                       .:..:+*#*++*:.                 ..:::---=+++.  .                            
                                                       .....=+=++*+-:.              .:::::--+=++*: .                              
                                                        ....:===-=*#+-:..    .:..:::--::-=-+====.                                 
                                                         ....:==---+++==--:-------:::---=+==+--...                                
                                                  .     .......=---:-=++#-:=-::--=--:-*+=---=.                                    
                                                   ..  ..........:.-::--:-::::-:--====+=++:..                                     
                                                      ......... ....:-:::::--=++=-:--==.....                                      
                                                       ......           ....---+:....  .                                          
                                                         ..           .:.:+-+  :*.                                                
                                                             .       . --=.:-=:.                                                  
                                                                         . ..                                                     
                                                                                                                                  
                                                                                                                                      
                """;
        FRAME[50] = """
                                                            ...::::::..::----:::....                                                          
                                              .:::::-----------==-:--..:....                                                      
                                           . ..:---===**++**=--=*#*====-...:-::.                                                  
                                          ....:-==+#+-.:-===++**###*==*#=-:--:::.                                                 
                                         ..:-===+##==*+::=%*++==--:::::::-------:                                                 
                                        .::.:==+*=+##=----=+*-...::..:.::::--:::::                                                
                                      ...::--=*=-+*+-----::::........:::::-:--:...                                                
                                     .....:=*#*:==*=-..::....   .:::::::::::-+*:.                                                 
                                       ..:--==-:==*+-:..:....   .::::::--==+#+++:.                                                
                                     ....::----=+++*=-::::::...::--:::--===-----::..                                              
                                   ........::---**+=*+===--------=-=+=--==--:::::.:::..                                           
                                           .::-::+#*==-==---=++*==+***++++=--:..::---: ...:+.                                     
                                           ...:--.:-=*%%#*##=-----==+*####**-:::--=====+::=#+                                     
                                             ..:-::...::-+**+##%%#%##***#**====-=-+++=+:--::..                                    
                                           .  .:..:::::::::--:::::-::-=+=+++*+#*##*#*+---:---.:                                   
                                         ..-.:.+..-.....:..---=------=::::.:::::.:-.::+##*#+-:..                                  
                                          .=::: ::::-=:-:-:::::-::::--::.:::::-::.-.:.:-:=*%##-:..                                
                                        .:+::=----:.:----::::::::::.....  ..:.::..::::::..:-+%%*:..                               
                                        ::::..:-:::--=+*===--::......       .:.      ...::..:+*##:.                               
                                       =-:::..:---:-=*=====+=+-...                     .::::::-+%*..                              
                                       --:..:==:....:::.+*+*+=+=::.                     .:---::=*#:                               
                                     ++..:-:-. .    ....:-=*+*+==-:.                     .:-:::-+#-..                             
                                        :-=.          ...:-=***===:.                      --:---+*-..                             
                                                      ....:=*#**=+-:.                  . .-:--=+*+....                            
                                                       ....:+*%*+=-..                .:.::-:===+=+:..                             
                                                       .:...-==++++-::..     .  .. :--::::--+=+=#.. .                             
                                                      :+=--.:=++-=*#*-:::...::::------::-:-*==+=:                                 
                                                      =*=-=::=*+=:-=*+++-==-==-----::---=+==++-:...                               
                                                        .:+:--:*------+-=*==-=-=+=:--=**+=-::.                                    
                                                        ....... .----=-::-:-::-::-=++=-::==..                                     
                                                        .. ...   ...-.:::::::::::--::-:.. ...                                     
                                                        ..      ..........-:::-:-:-...                                            
                                                         .         ... .    ..  ..                                                
                                                                         ...                                                      
                                                                                                                                  
                                                                                                                                  
                                                                                                                                      
                """;
        FRAME[51] = """
                                                          ..::..:::::..::--:-:::....                                                          
                                              .:::::-------------::-:.::.....                                                     
                                           ....:-----=+===**+:-=*#+---=-...:-:::.                                                 
                                          ...:::-=+***#*==**===*##*=-=*+-::--::::.                                                
                                         .::--===*+:=-+**##*++===-:::::.:::::-:-:.                                                
                                       .....:-=+-:+=-----=+==-:........::::--:...:.                                               
                                      ...::--=++-*+=-::-:.:-:........::::::.-::...                                                
                                     .....-=+#*:-+*=-...:....   :.:::.:..-::-=+-                                                  
                                       ..::=--=:-+#=-:.......  ..::.::::--=**+==:.                                                
                                     ....::::---==#*=-:::::...::::-::::--===---::...                                              
                                    .     .:::-=++++**+==-:------=====------::..::::::..                                          
                                           .:.:-:=#*==++=---===+==**++++***+-:..::----:..                                         
                                             ..:::-=+###*##=+-=-=--=++*###**-:::--==+=-:::..                                      
                                             ..:--::.:---+***%#%#####+%*#++=+==-==++==-==-:::                                     
                                               .:..::::::::--:-----:-==+=+**%*#*####+===+*=--+-==+=.                              
                                                ...........------=:---::::.::::.::--+=*%%#+=*%:. .+%-                             
                                    :=  :   ..-#+:::......:::::-:::::-::.:::::::-:.-...:--+##*=*-..+= =                           
                                  + .::.=::--:==++**=+-+-:::::::::::....  . ......:::-:.:...-*%*:---..                            
                                   - -==-:-----=====+=++++=+-::......       ...      ..::::.:-*%*-:=-                             
                                  .-:::.::::------===+*+==*++*::...          .         .::-:::-+#=.                               
                                   .=..::.:::==::::---::-++++++=:..                     .--:-::=##.                               
                                  .-::..:-*:..      ...:::-**++++-::.                    .:-::-=**-..                             
                                  ..---:-=.           ...:-+***++=:..                     :-:-:-**-:.                             
                                  :-:.-+=            .:::.:-=##+==:..                  . .-:-=-=+*-..                             
                                  :=. ..-           :+#+:.-####%+=-..                .:.:::--==+*+:..                             
                                                        .:-:-#==**#*:::.    ....-:::-:::::-===++=:...                             
                                                         ..::====-**+=::::..:::::----:::-=-*+==+:..                               
                                                        .....:=+:-==*+==-=--+==----::--:+**==-=:..                                
                                                         ......-:-----=+=+#---====:-:-===--+-..                                   
                                                         .......:=--=-=-:-:-:-:--===---:--=:..                                    
                                                             ..   .::::--:::::::-:-:=++-.....                                     
                                                                .........:-::.-=:.:...                                            
                                                                           ....... . .  ..                                        
                                                                         ...                                                      
                                                                                                                                  
                                                                                                                                  
                                                                                                                                      
                """;
        FRAME[52] = """
                                                         ....:..::.:....::::::......                                                          
                                           ...::::::-:--:--:-::::::-:.:.  .......                                                 
                                           ...:::---=+====++-:--=+=-:---...:-:....                                                
                                          ..::::--++++**+=+=---*#*=---=-:::.::::...                                               
                                         ..:::--=*#*#*+#*++++*===::.:...:::.:-:.::.                                               
                                       ....::--=+*+**=-::------:.........::::::...::                                              
                                      ...:---===+-:+-::::.:---.......:::::::::::...                                               
                                    .  ..:-=+++-.=*-:...:::..  .........:::----:.                                                 
                                       ..::-----:+==-::.....  ..:::::::::-==+=+-..                                                
                                    ......::::-:++*++-:::::.....::::::::--:--:::....:..                                           
                                          .:..--*#***++==-:-------====--==--::..:..::::.                                          
                                           ....--==+*#*+==-----==+++===+###+-:.:.::--:::..                                        
                                             ...--:-**##++---=-+++++==+#%%#*::::--+*++=-:....                                     
                                             ..::---.:::+++###*#######*+*+*=--==-+++==+*+=-:..                                    
                                                ..::::...::-:=--:::--==++*+***##%*#++:=+**=:::...                                 
                                                ..........::----------:-::::::::-:-+=*##%#+=--:..                                 
                                            ....::::..........:::..::::::...:.::--+::.:-=*##*+--::..                              
                                      . ...=-#=%*%#%=%-=+--:::..::.....     .....::::-:::::-+#%+-:.                               
                                    *:*+-#**==------+==**#++=.........      ...      ..::::.:-*%*-..                              
                           :.     :-=+-*+---=-=----===+*+=**+*-:....         .         .:--:::-*#=..                              
                             -   :=-::+=-:--=:----.:::.==#*=+***:.:.                    :----::+%#-**.                            
                              ::---.---:--=.... .   ..:-+-+**+++#=-:..                   ..--::-**%-.+:                           
                          .--.=+=+--=--+:..        .=-...:-=*****=:..                     ::-::=*++=-.=+                          
                            .*==*+=:-=-..          . .....:=+##*++-:..               ... .-:-=-=**==..==::                        
                            .++-==-:+*=.              ....:-=##*#+=:..       .  . ...::..:::=+=+**==.+==                          
                             . =+-*--=+:               ...:--=*=+*%#+:::    ...:-:--=-:-::-===+++:.   .                           
                             . :-:+:+:=-.               ...::=+---+*=--:::--:--:-----:-:--++++++-..                               
                              .  ..+  .                 . ...:==--==**-==++==+===-+-:---=*++-=-:.                                 
                                  -=+                    .....:-=+=---=+==#=:*=---+--=+==--+:::.                                  
                                                           .....:=:--=:--::::::-==--=--==-=:..                                    
                                                             ......::::.::--:-::::--=+-......                                     
                                                                .  ......:---.=-.:....                                            
                                                                    .     ...::... .                                              
                                                                          ...                                                     
                                                                                                                                  
                                                                                                                                  
                                                                                                                                      
                """;
        FRAME[53] = """
                                                         ....:..::.:....::::::......                                                          
                                           ...::::::-:--:--:-::::::-:.:.  .......                                                 
                                           ...:::---=+====++-:--=+=-:---...:-:....                                                
                                          ..::::--++++**+=+=---*#*=---=-:::.::::...                                               
                                         ..:::--=*#*#*+#*++++*===::.:...:::.:-:.::.                                               
                                       ....::--=+*+**=-::------:.........::::::...::                                              
                                      ...:---===+-:+-::::.:---.......:::::::::::...                                               
                                    .  ..:-=+++-.=*-:...:::..  .........:::----:.                                                 
                                       ..::-----:+==-::.....  ..:::::::::-==+=+-..                                                
                                    ......::::-:++*++-:::::.....::::::::--:--:::....:..                                           
                                          .:..--*#***++==-:-------====--==--::..:..::::.                                          
                                           ....--==+*#*+==-----==+++===+###+-:.:.::--:::..                                        
                                             ...--:-**##++---=-+++++==+#%%#*::::--+*++=-:....                                     
                                             ..::---.:::+++###*#######*+*+*=--==-+++==+*+=-:..                                    
                                                ..::::...::-:=--:::--==++*+***##%*#++:=+**=:::...                                 
                                                ..........::----------:-::::::::-:-+=*##%#+=--:..                                 
                                            ....::::..........:::..::::::...:.::--+::.:-=*##*+--::..                              
                                      . ...=-#=%*%#%=%-=+--:::..::.....     .....::::-:::::-+#%+-:.                               
                                    *:*+-#**==------+==**#++=.........      ...      ..::::.:-*%*-..                              
                           :.     :-=+-*+---=-=----===+*+=**+*-:....         .         .:--:::-*#=..                              
                             -   :=-::+=-:--=:----.:::.==#*=+***:.:.                    :----::+%#-**.                            
                              ::---.---:--=.... .   ..:-+-+**+++#=-:..                   ..--::-**%-.+:                           
                          .--.=+=+--=--+:..        .=-...:-=*****=:..                     ::-::=*++=-.=+                          
                            .*==*+=:-=-..          . .....:=+##*++-:..               ... .-:-=-=**==..==::                        
                            .++-==-:+*=.              ....:-=##*#+=:..       .  . ...::..:::=+=+**==.+==                          
                             . =+-*--=+:               ...:--=*=+*%#+:::    ...:-:--=-:-::-===+++:.   .                           
                             . :-:+:+:=-.               ...::=+---+*=--:::--:--:-----:-:--++++++-..                               
                              .  ..+  .                 . ...:==--==**-==++==+===-+-:---=*++-=-:.                                 
                                  -=+                    .....:-=+=---=+==#=:*=---+--=+==--+:::.                                  
                                                           .....:=:--=:--::::::-==--=--==-=:..                                    
                                                             ......::::.::--:-::::--=+-......                                     
                                                                .  ......:---.=-.:....                                            
                                                                    .     ...::... .                                              
                                                                          ...                                                     
                                                                                                                                  
                                                                                                                                  
                                                                                                                                      
                """;
        FRAME[54] = """
                                                         ....:..::.:....::::::......                                                          
                                           ...::::::-:--:--:-::::::-:.:.  .......                                                 
                                           ...:::---=+====++-:--=+=-:---...:-:....                                                
                                          ..::::--++++**+=+=---*#*=---=-:::.::::...                                               
                                         ..:::--=*#*#*+#*++++*===::.:...:::.:-:.::.                                               
                                       ....::--=+*+**=-::------:.........::::::...::                                              
                                      ...:---===+-:+-::::.:---.......:::::::::::...                                               
                                    .  ..:-=+++-.=*-:...:::..  .........:::----:.                                                 
                                       ..::-----:+==-::.....  ..:::::::::-==+=+-..                                                
                                    ......::::-:++*++-:::::.....::::::::--:--:::....:..                                           
                                          .:..--*#***++==-:-------====--==--::..:..::::.                                          
                                           ....--==+*#*+==-----==+++===+###+-:.:.::--:::..                                        
                                             ...--:-**##++---=-+++++==+#%%#*::::--+*++=-:....                                     
                                             ..::---.:::+++###*#######*+*+*=--==-+++==+*+=-:..                                    
                                                ..::::...::-:=--:::--==++*+***##%*#++:=+**=:::...                                 
                                                ..........::----------:-::::::::-:-+=*##%#+=--:..                                 
                                            ....::::..........:::..::::::...:.::--+::.:-=*##*+--::..                              
                                      . ...=-#=%*%#%=%-=+--:::..::.....     .....::::-:::::-+#%+-:.                               
                                    *:*+-#**==------+==**#++=.........      ...      ..::::.:-*%*-..                              
                           :.     :-=+-*+---=-=----===+*+=**+*-:....         .         .:--:::-*#=..                              
                             -   :=-::+=-:--=:----.:::.==#*=+***:.:.                    :----::+%#-**.                            
                              ::---.---:--=.... .   ..:-+-+**+++#=-:..                   ..--::-**%-.+:                           
                          .--.=+=+--=--+:..        .=-...:-=*****=:..                     ::-::=*++=-.=+                          
                            .*==*+=:-=-..          . .....:=+##*++-:..               ... .-:-=-=**==..==::                        
                            .++-==-:+*=.              ....:-=##*#+=:..       .  . ...::..:::=+=+**==.+==                          
                             . =+-*--=+:               ...:--=*=+*%#+:::    ...:-:--=-:-::-===+++:.   .                           
                             . :-:+:+:=-.               ...::=+---+*=--:::--:--:-----:-:--++++++-..                               
                              .  ..+  .                 . ...:==--==**-==++==+===-+-:---=*++-=-:.                                 
                                  -=+                    .....:-=+=---=+==#=:*=---+--=+==--+:::.                                  
                                                           .....:=:--=:--::::::-==--=--==-=:..                                    
                                                             ......::::.::--:-::::--=+-......                                     
                                                                .  ......:---.=-.:....                                            
                                                                    .     ...::... .                                              
                                                                          ...                                                     
                                                                                                                                  
                                                                                                                                  
                                                                                                                                      
                """;
        FRAME[55] = """
                                                       .................:.....::.     ........                                                
                                           ......:::::::..::.....:::::.. ....:.....                                               
                                        ........::::---:::::.::::-:::..............                                               
                                        ::..:::---+=--==--::--==-::::...............                                              
                                        .::::----=*#*--------=+=:.... ..........   ..                                             
                                       ...::-----===-::::-----:::.  ........::...  ....                                           
                                       :::::-----++--:..:::::-::..  .......:::....                                                
                                       ..:::-=-----=-:....:::.....  ......::---:..   .                                            
                                      ....:::=**===--::.:::. ...........::::::::.. .....                                          
                                          ..::-###=--::..... ...:::::::::::::::.........                                          
                                           ....*=+**+++==--:::::=-::::::-==-::.......:::.                                         
                                             ...:==+=+=----::::-+=:::::=**#=-.....:::---::                                        
                                               .:======---:--:::=-----=+*#*=-::::---:--=-=:.                                      
                                               ..::::.:::::..::::.::+=+*+==*=:::==+****+**:..                                     
                                                 .:........:::--:...:=+++*#**#**+++=+***+-::....                                  
                                              . ...:.......::----=+==+=-::::==::-==+*#%#*+=-++-....                               
                                       .  .....::::::::...........:::.:::.....:-=---::--+*%%+*+=:...                              
                                    .....:::-:::-------=:::........... ..     ...::::-::.:-*#%*==-.                               
                                   ..:::::-----=============.........                 ..--:::*##+--.                              
                                   .:::-==--=---==---+++====*=:......                  .:---:-+#*-:.                              
                                  .:---=+--===+-=:--=--:-*+**+*:::::..                 .:::==:-*%=...                             
                                 .:-=-*=-++-:.......::-:--=***+%*-::...                   :=-:-+*=:...                            
                               ..-==--*+=-=*-  .    ...:::-=*#*+*=::. ..                  .:::-+*=--..                            
                              .::-=++-=..  .+          .::--+##*#=:::..            ..... ..:===*+*#*=..                           
                             .::-++#*+.   .--           ..:==*%%+#---:..     .::-::::::-..:-==++*=-:.                             
                             .:=--+%#:.                 .:-:=*+=+*#*++:.......:-----=:::----=+++=:.                               
                             .:---*%#:.         ...       ..:+====+=-----==+=+++-=++===---=+===--.                                
                             ..---=#%=:.     .:::.-:=:      .:==--=====---=++++*+*##+---++==+=-:..                                
                              ..:::=###-..:.=:-:-+*=+       ...==-:-:-==+==*=+=--=:-=+*==+++==-:                                  
                               .::.::+###+=-::#*++%-    .       .:-:--=-:::::::=--==----*=:..:: .                                 
                                .----:-----+++*+*:=-   :          ...:..::==+**+:-:--:...:..                                      
                                .::-=+--=*++++==:.                      .---:::::=#=.                                             
                                        .: ...-+:  .                     :::..... .=*.                                            
                                          =   -:                           :=-::--.                                               
                                              .                            .:: :                                                  
                                       :                                                                                          
                                        .                                                                                             
                """;
        FRAME[56] = """
                                                       .................:.....::.     ........                                                
                                           ......:::::::..::.....:::::.. ....:.....                                               
                                        ........::::---:::::.::::-:::..............                                               
                                        ::..:::---+=--==--::--==-::::...............                                              
                                        .::::----=*#*--------=+=:.... ..........   ..                                             
                                       ...::-----===-::::-----:::.  ........::...  ....                                           
                                       :::::-----++--:..:::::-::..  .......:::....                                                
                                       ..:::-=-----=-:....:::.....  ......::---:..   .                                            
                                      ....:::=**===--::.:::. ...........::::::::.. .....                                          
                                          ..::-###=--::..... ...:::::::::::::::.........                                          
                                           ....*=+**+++==--:::::=-::::::-==-::.......:::.                                         
                                             ...:==+=+=----::::-+=:::::=**#=-.....:::---::                                        
                                               .:======---:--:::=-----=+*#*=-::::---:--=-=:.                                      
                                               ..::::.:::::..::::.::+=+*+==*=:::==+****+**:..                                     
                                                 .:........:::--:...:=+++*#**#**+++=+***+-::....                                  
                                              . ...:.......::----=+==+=-::::==::-==+*#%#*+=-++-....                               
                                       .  .....::::::::...........:::.:::.....:-=---::--+*%%+*+=:...                              
                                    .....:::-:::-------=:::........... ..     ...::::-::.:-*#%*==-.                               
                                   ..:::::-----=============.........                 ..--:::*##+--.                              
                                   .:::-==--=---==---+++====*=:......                  .:---:-+#*-:.                              
                                  .:---=+--===+-=:--=--:-*+**+*:::::..                 .:::==:-*%=...                             
                                 .:-=-*=-++-:.......::-:--=***+%*-::...                   :=-:-+*=:...                            
                               ..-==--*+=-=*-  .    ...:::-=*#*+*=::. ..                  .:::-+*=--..                            
                              .::-=++-=..  .+          .::--+##*#=:::..            ..... ..:===*+*#*=..                           
                             .::-++#*+.   .--           ..:==*%%+#---:..     .::-::::::-..:-==++*=-:.                             
                             .:=--+%#:.                 .:-:=*+=+*#*++:.......:-----=:::----=+++=:.                               
                             .:---*%#:.         ...       ..:+====+=-----==+=+++-=++===---=+===--.                                
                             ..---=#%=:.     .:::.-:=:      .:==--=====---=++++*+*##+---++==+=-:..                                
                              ..:::=###-..:.=:-:-+*=+       ...==-:-:-==+==*=+=--=:-=+*==+++==-:                                  
                               .::.::+###+=-::#*++%-    .       .:-:--=-:::::::=--==----*=:..:: .                                 
                                .----:-----+++*+*:=-   :          ...:..::==+**+:-:--:...:..                                      
                                .::-=+--=*++++==:.                      .---:::::=#=.                                             
                                        .: ...-+:  .                     :::..... .=*.                                            
                                          =   -:                           :=-::--.                                               
                                              .                            .:: :                                                  
                                       :                                                                                          
                                        .                                                                                             
                """;
        FRAME[57] = """
                                                          ...............     ...     .  ..                                                   
                                           ........:::......  ....:.... .   ....                                                  
                                      .... .....:.:::-:::......:::...         . ...                                               
                                      .....::::::-----:::::...:::....         ...                                                 
                                       ..:::-:::::--=-:::::::--::..    .    ...                                                   
                                        .::::::::----:.::::::-:.::         ......      .                                          
                                      .:..::::-:::----:.::..:::...       ...:::...                                                
                                        ...:-::----::::.:......     .........:...                                                 
                                        ...:-:=+:-:::::.....:...:.....::.:.:.....   ....                                          
                                           ..:-==+-:::::..::....:......:::::.....   .....                                         
                                             ..-:-===---::::::.--.......:---:...  .::::::.                                        
                                               .---=----::::::.-=:::::.-=+=-:::..:::..:.:::                                       
                                               .:-::::-:::::::::-:::-----+----:.:------:-=-.                                      
                                                ::.....::-----====-===--=--=--::--=++=+-+=:...                                    
                                                ...... .  ...:-+=+*#*+++---=-:::---+***=-:::-::..                                 
                                             ....::.....  .:..::-=+++++-:-:-:.:....:-#+*++-=++--:..                               
                                    ..............:::.::..:......:-::.::....  ::-:..:--++*#+*#==-..                               
                                  ....::::::-----=-===-=-::..... ...    ....   ..:.::.:=--=*###*+-:-                              
                                  ..:::---=-----=-======-==-:....                    ..::--:=%#+--::                              
                                   ::--+-=-----------+=*+==+=--:.....                 .:::==:+**-::.                              
                                  .:+-+--:-+--+-==:---=---**=*+==::.:.                  ..-+-:+*=.....                            
                                 .-=-=--==-=-:::::::::--:-=***+#*-::..                    .=-:=+#:....                            
                               ..:==:==+=:. ......+:-=--::-=+#*+*+-::..                   .:--=+*----:                            
                             ...:=*=+-=:.          :-=-.-+:-=*##==--:...      ....::....:..:=-+*#+===-.                           
                             .::-=+*#=:...         ==:-::-*:-*%%*+***-...   ..:::::::::::::-===*+:....                            
                             ..:-=*%+-..           - :-.:+#*==*=++#*=+--:-::.:------==----+==++==:.                               
                             ..++*#%#=+-.-=.    . .::=+=:-=:::=+--==--=-==-=+=+=--=++**===++=+-:-.                                
                             ..:=++*#-:.  ... ...:::-#+-*-:..:-==::+*+===--=***###**+--+*=-+----..                                
                              ..::*=*#+::....:::--=*%*.**+-:..---=--+*-+===+=*=-:-=-*++++-*-=--:                                  
                              .....:*+#%*++==-=#+%%#:.=. .--...-+=::------:-:=----====*=--:.:-=:.                                 
                                 ...:.:=+*+*++-==:::..=     .:==+.....::::.:.:-:.:-:::...:.                                       
                                   ..:::::::::::....                  ...:::--::........  .                                       
                                     ..   .                        .. . .:::..... ..     .                                        
                                                                         ......                                                   
                                                                                                                                  
                                                                                                                                  
                                                                                                                                      
                """;
        FRAME[58] = """
                                                            ......             .                                                              
                                           .........:...   .  .......                                                             
                                      ...  .......:::::.........::.                .                                              
                                     ......::::::::::::.......::...                                                               
                                       ..::::.:::::::-:::::::-::...         .                                                     
                                      ......::::::::::.....::::....         ....        .                                         
                                      ....::.::..:--:::.:...:::.           .......                                                
                                          .:::::--:::::::...... ..   ...........  .                                               
                                          .:::--:::::.:..................::.....      ..                                          
                                            .:-----::-::...::..:........:::::....  .....:                                         
                                             ..::-----::::...:.::..... .:--:::... .:.....:                                        
                                               :::--:::::.....::-...:.::----:::.:::::....::.                                      
                                               .:::..:::.::::..:-:::--:------::::----------:                                      
                                                :.....:.:::::---------::------:::--+--=-=-:::.                                    
                                                .......    ...:==-=++=-======---=+*#*+*=-:-=--::.                                 
                                      .    ......::.:.... .....:-=++==+-:---:----::-=++++=--++---                                 
                                   .....   .:......:::::..::...::--:::::... . :-==+*- .-::==*#+-::                                
                                 ....::.:.::--:---=-====--:....  .:..  .....    ...:::::..++-+*+=-::.                             
                                  .::::--=---=-==++=====++=-::...                    ..:::..=#+==-:..                             
                                  .:-===------:-------=*+*+=+=-::....                ...::=+=+#-::..                              
                                 .:===+=-=--++-==  :.::+-+#**=**--:...                  ..-#*=**:...                              
                                 :-==--=*+++=::--:..... --=+**+#*=::..                    .-+-+#=:....                            
                               ..-==--*+-:........=.--:::---+****+-:::.                   .:--+*+=-:--.                           
                             ..:-=++=+=:...     .. .:-=-:==--=-=+===--..      ..:.::::...:.=--*#*=::--:.                          
                            ..::=+###=-...         .::=*-:---+==-++*+-:..  ..:::::::---::::-=*#++:....                            
                             ..-=+#%*-:...         .:::-=++-:++==*#*+=---=-::--=-------=---=+++--:                                
                             ..:-+#%+-::..:..   ..:::-++-:::--==+=++----====+==+====+**-=+*++=--::                                
                             ..::-*%#=-:.--:...:---:-##=-:::::---==++*++++-=+****##*=-==+=+===--:                                 
                              ..:.=*%*+=-+:::::--:-+%#-:......:--=-==--==--=+=+---=-=+=-:=+=-==.                                  
                               ..:.:+##%+=-=---=+###*::.........---:-:-----:----=-::--:+--::.:-.                                  
                                  .::.-=**#***+==-::...       ... .....:-:.:.=:.===:...:::.                                       
                                   ..:-++---..... ..            ...    ...::::::..........                                        
                                     :=. -:.                      ....  ..:.....                                                  
                                                                         ....                                                     
                                                                                                                                  
                                                                                                                                  
                                                                                                                                      
                """;
        FRAME[59] = """
                                                            ......             .                                                              
                                           .........:...   .  .......                                                             
                                      ...  .......:::::.........::.                .                                              
                                     ......::::::::::::.......::...                                                               
                                       ..::::.:::::::-:::::::-::...         .                                                     
                                      ......::::::::::.....::::....         ....        .                                         
                                      ....::.::..:--:::.:...:::.           .......                                                
                                          .:::::--:::::::...... ..   ...........  .                                               
                                          .:::--:::::.:..................::.....      ..                                          
                                            .:-----::-::...::..:........:::::....  .....:                                         
                                             ..::-----::::...:.::..... .:--:::... .:.....:                                        
                                               :::--:::::.....::-...:.::----:::.:::::....::.                                      
                                               .:::..:::.::::..:-:::--:------::::----------:                                      
                                                :.....:.:::::---------::------:::--+--=-=-:::.                                    
                                                .......    ...:==-=++=-======---=+*#*+*=-:-=--::.                                 
                                      .    ......::.:.... .....:-=++==+-:---:----::-=++++=--++---                                 
                                   .....   .:......:::::..::...::--:::::... . :-==+*- .-::==*#+-::                                
                                 ....::.:.::--:---=-====--:....  .:..  .....    ...:::::..++-+*+=-::.                             
                                  .::::--=---=-==++=====++=-::...                    ..:::..=#+==-:..                             
                                  .:-===------:-------=*+*+=+=-::....                ...::=+=+#-::..                              
                                 .:===+=-=--++-==  :.::+-+#**=**--:...                  ..-#*=**:...                              
                                 :-==--=*+++=::--:..... --=+**+#*=::..                    .-+-+#=:....                            
                               ..-==--*+-:........=.--:::---+****+-:::.                   .:--+*+=-:--.                           
                             ..:-=++=+=:...     .. .:-=-:==--=-=+===--..      ..:.::::...:.=--*#*=::--:.                          
                            ..::=+###=-...         .::=*-:---+==-++*+-:..  ..:::::::---::::-=*#++:....                            
                             ..-=+#%*-:...         .:::-=++-:++==*#*+=---=-::--=-------=---=+++--:                                
                             ..:-+#%+-::..:..   ..:::-++-:::--==+=++----====+==+====+**-=+*++=--::                                
                             ..::-*%#=-:.--:...:---:-##=-:::::---==++*++++-=+****##*=-==+=+===--:                                 
                              ..:.=*%*+=-+:::::--:-+%#-:......:--=-==--==--=+=+---=-=+=-:=+=-==.                                  
                               ..:.:+##%+=-=---=+###*::.........---:-:-----:----=-::--:+--::.:-.                                  
                                  .::.-=**#***+==-::...       ... .....:-:.:.=:.===:...:::.                                       
                                   ..:-++---..... ..            ...    ...::::::..........                                        
                                     :=. -:.                      ....  ..:.....                                                  
                                                                         ....                                                     
                                                                                                                                  
                                                                                                                                  
                                                                                                                                      
                """;
        FRAME[60] = """
                                                         .  .....                                                                             
                                              .  . ....           .                                                               
                                            .........:..       ...                                                                
                                      ..:.......:.::.:...     ....                                                                
                                        .........:::::..... .......                                                               
                                     .....:..::.....:::............                                                               
                                       ......:........:..........     .                                                           
                                         ....:::.:::::.:........    .......                                                       
                                        .....::::::::......  ..        ......         ...                                         
                                           ..::..:::::...........    .. ........ ...... .                                         
                                               ::::::..........::. ......:::......::.......                                       
                                               ...:::...... ...::..:::..::::::::::::::.....:.                                     
                                        -.  .  .....:...........:::::..:::::::::-::-:::-::::.                                     
                                       =*---. .:... ..........:::-:::::::::-:-:::-==:--::::-:::.                                  
                                        .+*%=--:::......     ...:-:--:::-==---:.--===+=--::::::::                                 
                                   ....  -++#=-==-::-...........:--++==:::-:::::-=+==**+=---=+-::.                                
                                 ......   :-*=*++--+=:.:-::::..::-=-:::..    .:-=+=---=-++==*#=--:.                               
                                .....::::::=+-=++==::**===-::....::..  ..       ......:::--=++*+-::...                            
                                 ..::---===----+=+++=+==+++=-::.. ...                 ...-==*+=---...                             
                                 .:=*+===----:----=--=-++=++=++=::....                ....=++#*-::...                             
                                .:==-=--::-=+**:--:.-*-:-==#*+*#*-...                .   .-#*%*-:..                               
                                :-=+---==***+::=++--=+.:++-=*#+**=::..                    .-=%#=-:...                             
                              ..--==-=-+=:.......:=----:-==:=*#*+=--::.            ..    .:+-%%#+=-:::..                          
                             .:--+++*=+-:..     ...:--+*-:++-=##+=-=*+:.     .......::::..::--=**-..::::.                         
                            .::-=+*#++=-...       ..:-=%*-=+=-*#+*#*=--:...:....-::::::--::+-:*=-:...                             
                             .:=+*%%=+-:.....   .-+---=%+---+=+===*+=---------=--------=+*==::+--.                                
                            ..:-=*%%=--:.......:=-#+:-+#+----=-=--=++++=+=--======+++*#*++**+=-::.                                
                             ..::=*%#==--::::::--+-=-**+---=::--+---+*#*#+===#+**###+--=++*+==+:.                                 
                               ..:++%%:+#+===-=++:=+#%*=:.---:::-=--:----=:===-.=--*++-=*=---=-.                                  
                                .::==##**-::::-===%##:.-=+.....--::::.:-:-:-:----:::-+=--::::::.                                  
                                 .:::-+*##%##***++-:...-=:      ..    ..::.-:==--::.....::    .                                   
                                  ...:.::::.:.::::...                   ......:::.      ..                                        
                                     ....:::-:....                      .......                                                   
                                                                         :                                                        
                                                                          .                                                       
                                                                                                                                  
                                                                                                                                      
                """;
        FRAME[61] = """
                                                         .                                                                                    
                                                 .   .            .                                                               
                                           .............       ...                                                                
                                      ..................      .. .                                                                
                                          .......:..:.....   . ....                                                               
                                     .................:............                                                               
                                         ........................     .                                                           
                                        .....:::..:::.:.......           ..                                                       
                                           ...:::..::......              ...        .   .                                         
                                          ........:::............        .....   .:..   .                                         
                                      -=.      .::::.....    ...:  ......::......::.........                                      
                                      *%-.     ...:.....      .....:.......::..:::::.........                                     
                                 .   :-==+-. . ..  .............:::::...:::::.::::::...::::::.                                    
                                      ::=%+===-:... .. .......:::::::::::::::::::------::::::::.                                  
                                      :===++++=:........     ...::::-:::-=---::.:---===-::::-:..:                                 
                                  .....:-==++=++-:.::...........:---+--:::-:::::--=-=+++----===::.                                
                                ....:.. .-+-:**=+==:-:::-::::....-==.....     .-++-::---=+--+#=--:.                               
                                ....::::::-+.---==*+#**#+=---....::.. ...       ......::----==+=-::...                            
                                 ..::--===---==+=*+*+=+=+===-::......                  ..:=-+==-:::..                             
                                 .-+*+===--:=----+++=-==+**=*+==-:...                  ...-===+=.:...                             
                                .-+*-=-=--:==**+=+:  .-=-+=*#+##*=:..                 .  .-#***-...                               
                               .---+---=+=+*+-=--++-:..-+=-=*****+:...                    .-=**=:...                              
                              .:-====-+*=.....:..:-=---.-=*=-*##+=--:-.            .......:+*%%#*=-::...                          
                              :-=+++*+*-:..     ...:--**=-+=-=##++====-.     ........::::.:=+%##*::...:::                         
                           ..:--+###**+-:..       .-==+#%:=+=-*#+*#*=-::::.....::-::::::---%%**=-:. ..                            
                             .:=+#%%=+--.....   ::-=+=-**:--+-+*=+++=---=-::--=--------=+::##*+-::                                
                             .:-=#%#==-:.......:-+##-=+#=:=----+-==+**+=+=----+====++++=:.:-++--:.                                
                             ..:-=#%++==--::::::=+*-==#*--::-----==-++*****=+**+***##===:.----+:.                                 
                              .:::+#**+##*++++**=::=#%#=:.....::--=-=--:--=====-+-+=+=*#*-:-:--.                                  
                               .:-:-+%%*=-:-:---=**%#+:::......--:::=.::-:-:-=-:-=----=-:::::::.                                  
                                 .:-:-+##%%#%*#**+=-::...       ..    .....------::.....:.    .                                   
                                  ..:-:::::.::::::....                   ......::.      ..                                        
                                    ....::::::....                      ..    .                                                   
                                         .....                           .                                                        
                                                                                                                                  
                                                                                                                                  
                                                                                                                                      
                """;
        FRAME[62] = """
                                                                                                                                              
                                                                                                                                  
                                         .    ....   .                                                                            
                                      ...  ...........                                                                            
                                        ................                                                                          
                                     ....   ..... .......     ..                                                                  
                                            ...  .........     .                                                                  
                                        ..   .:..............                                                                     
                                      -:=:- .............                                                                         
                                   .:-:..-::.....::... ....  ..        ......     :.                                              
                                 :..::::-==+-............      ..  ....................... .                                      
                              .:  ..:---#++:   . ......        .............:...::::..........                                    
                                   .:.+=##=.   .   ....    .   .::..:......::..:::.::..:.::::.                                    
                                  . ::-:+**+:......  .   .....::::.::.::::::::::::::---::::::...                                  
                                    .:-::-*++=-::.....        ...::::.:::--:::..::-----:::::::..:.                                
                                 ......=.::-*+=++-:::............::----::-::..:::--:------:--=-::..                               
                             . ..........----+*+=+=---::--::::...:==:...   .  .-----:-----::=#=--..                               
                                .=--:=+-+--=+--=+++++#**+==--:...:::. ...        ..:..::-------=-::...                            
                                :-::-=-==+==---==+=*+=+++++==:::.....                  ...:-+-==-::..                             
                                 :=##*===---++=+-==-+==+=*+=*+===:...                    .-+=-=-:...                              
                               .:=++=-::---+##***+=:..-+-==**+#%*=:.                     .-+==+-..                                
                               :=+-+----+*+++=-==+*--:.:=====#***+:...                   .:++*+=:...                              
                              .-====+-=-=:......:--*+=-:-=*==**#+=--:-.            ...::..:-*###+-::.                             
                             :-==+++#**-:...   ...:---+*--++-=*#*#+=---:..   .........::::::+###*=........                        
                           ..:--+###=#+-::..     ..:--+%%==+*-*#***+=-::::.....:::-:::::--=++*+=-..                               
                            ..--+%%##+=-:....  :-==+*:-++=====++=++=----=-:--::-------==+###*+-::.                                
                            ..:-=%%*++--:::....-=*##--=**=+=--===--+***++=---:++++====+=#%%*=---:.                                
                            ..::-+%%-+===---:::-=+*-:=%%+-----:------+*+**+==+*#*++--++*#++-::-:.                                 
                              .:::*%%*=#%#++===++-:=+%#*-:....:::::-:-::-=-+=+##=:.::--++-:::--.                                  
                               .---=*%#*--:::::--+*%#+-::..   .-::..::::::---:=:..::::-::...:::                                   
                                 .:::=+#%%*##**##*+-::..         .    ......::::::... ....                                        
                                ....::.:::::::::::....                        ..:.      .                                         
                                    ....:::::....                                                                                 
                                        . ...:.                          .                                                        
                                                                                                                                  
                                                                                                                                  
                                                                                                                                     
                """;
        FRAME[63] = """
                                                                                                                                              
                                                                                                                                  
                                                                                                                                  
                                         .  .........                                                                             
                                       ..       . ......                                                                          
                                    . .    .....  .......                                                                         
                                   : .  . --:..  .........                                                                        
                              .   .--:-=---- .:..  ........                                                                       
                                 ...:::=-..:+=.........                           .                                               
                                .:.. ::.=-=++=-.......              ...    .      ..                                              
                                  ...:=--+::     ....           .  ..... . ...........  .                                         
                                  :::.-=*=.    .                ... ...  .......::::..........                                    
                                  ..-::-##=       .. .        ..:............. .::.::.....::...                                   
                                   :..::-#+*-.......      .......:.:..::.:::...:::::-::::.:::...                                  
                                   ....-:-**++---:...          ...:::.::::-:.....:::---::::::::.:                                 
                                .......::-:=+*=+*=-:.....:.......:::::::::::..:.::::----:::----:...                               
                                ........:-==:=*++===----=--:::..::-=:...   .  .:---::-----::-+=--... .                            
                                 ..:::::--==+-==++=+*++*=#+--:..::::....         ..::..::-=----=-::...                            
                                 :+*==+++----======+++++=+*+--::.....                  ....-=---+-..                              
                            :====--*=#===-==-=-===:-=--===#+=+==+-:.                     .-=----:...                              
                            +:---=-:--=-+*+*#****=-..:-=+--****%*=:.                      :=--=-..                                
                             ..-=+==--=*=+*#*====+*-:-.:=+==+****+:...               ...  -=+#*-:...                              
                              :===-=-**+=:......-==+---::=*+-*##+==-:::.            ....:..-+#**+-:..                             
                            .:-==+**-*+-:...  ...::-=-+*==++-=####+=--::.... ..........:::--+##*=-.........                       
                           ..:-=+###+#=-::..    ..:---+##-=**=+%#**=--:::::.. .:::::::::-+=*+*=-:.                                
                            ..-=*#%*#+--:...   :-=*##--=+=+======+*=-::------::--------==+#*++--:.                                
                            ..--+#%*#+=-:::....:-**#--=#*+++--=-+-=*##**+=-----**==+====**#*=----.                                
                             .::-*%#+++++--=----=+#-:=*%+=-:-----=--=***+=--==+#+=++######+-::-:.                                 
                              .:.:##%==%#**+==-==-:-%%%*-:.....::--:-:-------:.:--=**+==+::::::.                                  
                               .:--+#%*+=-:::----=+#%*-:..    .::....:.-:::-...::-=-::-::...:::                                   
                                 .-::=*#%%##%###*+==:...         .     .......::.::.   ..      .                                  
                                 ...::.:::::::::::....                        ....                                                
                                    ....:::::...                                                                                  
                                        .....::                                                                                   
                                                                                                                                  
                                                                                                                                  
                                                                                                                                      
                """;
        FRAME[64] = """
                                                                                                                                              
                                                                                                                                  
                                                                                                                                  
                                      :       ..                                                                                  
                                       .-.      .                                                                                 
                                  -::-=-::--+.=+-  ..                                                                             
                                .-:-:-:.:.==----..     ...                                                                        
                                  .:--: ::...=::-*- ..                                                                            
                                 . ...:.-:::=::=--....                                                                            
                                 .:::.:=-=--.       .                            . .                                              
                                 .:.::::-=:..     .                  .       .... ..                                              
                                  ...::=#+:.                    ..   .    ..... .....    ....                                     
                                   ..:::#%*.  ......            .......  ..... .::::..........                                    
                                    ..::=##+=:::...-:.:   .   . .........::.....::::::::..:.....                                  
                                    ...:::+*+=+++-::=#          .......::::......::::::.::::.....                                 
                                . .....:-=:-+++++=-::-:..::........:::::........:::::::-::::::::...                               
                                 ......::-=::=+**+****=::---::..::--:..   .   .:::::::::-:::----:...  .                           
                                ..:-::::--=+--=-+=+++*+++++=-:..:::....           ...:::::---:---:...                             
                                .-++=-++==------====+=++=+++=--:::...                  ..::----:-=:.                              
                               .:+##*+==-==---===-=::--==++=+===+=:.                     .-------:.                               
                              .---=+=+--=-:#####**-::::=+=++#*###+-..                    .:====-..                                
                             .:=+*+-==++=+**+=+===-:--:.=++-=*#++=:...               .....-=++*-:..                               
                           .****+=+=*++=-::.:..:+======-:=*+-=##*=--:...              ...:::=*=++-:..                             
                          ++.:+-=+=+=*+-::..  ..::--=-=**-+++-+%##+=--:......  .........:-:-=+**=:....    ..                      
                       ..=*=::--====-*=-:--.   ...:=+-=#%+=##==#%#+=--::::....:::::::::-=++=+++--.                                
                        - ::.:-=+#%*#+=-:......:-=+##--+%+++==-++++=--::---:-:::==::-----=*#=-::-.                                
                            .:--+#%*#*=---:::.::-+*#---*#+=+=-+-==-*#***=------*++===+==+***-:-::.                                
                            .:::=*%#-+++===-----=*#---#%*=-------=--+#*==-=---=*###++***#+=:.:::.                                 
                               :-**%#**##***+++-=---=##*-.... ..:--=--...::--=+#*++*+*+-=::.....                                  
                                .:=+%%#=----:----=+%%%=:..    .::....:.....:---=----:.:........                                   
                                 ..:-+#%##%*##*%**+=::..        .       ...  ....::    ..                                         
                                 ...:::::::-::::::...                          ..                                                 
                                    ....:::::..                                                                                   
                                        ........                                                                                  
                                             ...                                                                                  
                                                                                                                                  
                                                                                                                                     
                """; 
        FRAME[65] = """
                                                                                                                                              
                                       .                                                                                          
                                        .:.                                                                                       
                                   .   .  ::                                                                                      
                                    .+..-+::::=.   :                                                                              
                                   =-:==:::::---:=*:                                                                              
                                   .::::-:-::-:.:.==:                                                                             
                              .   .::...-:::::::-::+#-                                                                            
                                  .:+..::-:-:-=.   .                                                                              
                                ..::::-::==:.                                                                                     
                                 ....:::-+:..                                     .                                               
                                  ...::-**:..                              . ..  ....    ....                                     
                                    .::-#%+:....=+=.            .  ...    .... .::.:..... . ..                                    
                                    ..::=*#=*::-::=.              .....:.:.  .......:::........                                   
                                    ...-::+*+*++++*=..           ......:::..   ...::::::::.:.....                                 
                                   ....:-.:-==++*=--::::::::::...................:::::::::::::::...                               
                                 .....:::-:--***=++===--::---:..::::. .   .   .:.::::::::-::::--::.                               
                               ..:--::::--+**-==+=*+**+**+++-::::::...            ....::.::-::-:::...                             
                               .:+*=-=++==--=-===+++++=+==*+=--:::...                  ..::::-:::-:.                              
                              ..-=*+*+==-----+-==:------=***+++=+=:.                    .::--:::-:..                              
                             ..---==+--=++*#####*:::..-=*=-+##*%#*::.                    .:==---.                                 
                            .::=+#==--=*==*#+=====+---:--++==*##==:...               .....:=++*-:..                               
                           ..:+#+=====+--:.::..=====----:=**=+*#*=-::....             ...:::=*-==-.                               
                          ..::==+*+++*=-::..  ..:---=-=#+++++-+%#*=---:.....    ........:---==++=:.                               
                          ..::-+**#*#*=-::-.  .:.:-+*-=%%**###=###=--::::.......::::::::--==+=---.                                
                          -*%-==**=*%*=-:......:--+**-:=%#++==++*+==---::--:-::-+-:----::=+++-:::.                                
                          +.:++===++#*=-==:::::-=+*#-:-#%*===-=++=+*#+++-------=+=*==*==+=+=-::::.                                
                         *#...::=+=#=++++-------=*#-:=*%#=--:--:-=.::.:=------==*##+=+=-*+--...:.                                 
                        =:*+  .--=+##+###*#*++=+=----#%=-:......::::..:-----++=**==*=++--::.....                                  
                          :+-   :.-*#%%--------:-=+#%#=:..    .::.......:::::------:.....    ..                                   
                          ..    ..::-+*#%#%#****#*+=-:..        .       ..    ...::    .                                          
                                 ...-::::::::::::....                           .                                                 
                                    ....:::::..                                                                                   
                                       .........                                                                                  
                                             ...                                                                                  
                                                                                                                                  
                                                                                                                                      
                """;
        FRAME[66] = """
                                                       :.                                                                                     
                                             :                                                                                    
                                          .: .-:                                                                                  
                                        -. --...-:.                                                                               
                                      :-::--:..--+#=:  :                                                                          
                                     :-::-..:-=-::---=*:.                                                                         
                                    ..-::::.-:::::.:..:=:                                                                         
                                  .:::::=-..-=-.:..:.-.+=:                                                                        
                                 ..:::.-:.:=:                                                                                     
                                ..::::-.:--:.                                                                                     
                                .....:::==:... ..                                                                                 
                                 ...:::-**-::.:=                                  .        .                                      
                                   .--:-#%++==+#...                      ...... :......  .                                        
                                    ..::-#*++===---:.               ........   ..............                                     
                                    ..:::-*#+*+*=---:..           .........     .................                                 
                                  .....:-.:-=#+-*=--:::.::::.:.   .....  .   .......:::::::..:.....                               
                                ..:...::-==-:=#++*+++==---=--:.....:.        .....:::.:::::::::::..                               
                               .:--:::---=+*-:=+=+=+**#*++===::::::...             ......::::::::::                               
                               .-*=--=*#+=----=-=====*=++***+-::.....                 ..:::.::::::..                              
                              .-:-+=+++---:-=+-+-=:--=--=+****===--..                   .:::::::-:..                              
                             .:---+=+=-:-=+*####=+:-::-+#*-*#####+:..                    .:--:::.                                 
                            .:-=**+=-==*--=*=+====--:---=+++=#**+-:..                  ....:-==-::.                               
                           ..=#*+*=+=+=-::.:-:----+=-=---+**++##*=-::.  .              ..:::-+---:.                               
                           .--++++*+*+=:::.....::--=+-**#*=++++%#+--::::.        .......:-=--==-=:                                
                          .:::-=*#%+#*=-::::  .:::-=+-=*%#=###**=+=-::.::.  ....::::::.:--=---::::                                
                          ...--=*%%*#*=-::-....::-+**:-+##+*+=+.::-=-::::::::::----:::-::=+==-::.                                 
                          ...::-+%%*#*=-=-:::::-=+*#--=*#*==----:.:-*===-::::::-=+=+==-==--::..::                                 
                          ....::-*%*+++++=--:----+#--=*%*=--::-::-:--+*-----:-+==++====---:-.....                                 
                           . ..:--=:******#*+=-==--:+#%%-:......::::#+---------===+++---:......                                   
                           .. .:-=-=-*+==--------=*%%#=..     ....  .........:::::::....                                          
                            .-*===-=:=*%##**+##***+=-..        .               ....                                               
                             .*.....-:-*::::::::::...                                                                             
                               -+. ....:::::...   .                                                                               
                               :=*=--   .........                                                                                 
                                : -..         ..                                                                                  
                                                                                                                                  
                                                                                                                                      
                """;
        FRAME[67] = """
                                                           -                                                                                  
                                             .  -.                                                                                
                                         :   .=-:::                                                                               
                                        .::-*--+...::.    =                                                                       
                                      .:=:::-+=-..:-*++   :                                                                       
                                      .::::-:.:::..:-:=-:+-                                                                       
                                   .----+--=-:-::::-.::.:+-                                                                       
                                .....:.:=-:..--:...:.::.::#+                                                                      
                                ...::..-:.:+:            ..                                                                       
                                ..::.:-.:=-:.                                                                                     
                               ......:::==::..:-.                                                                                 
                                .....::-**++*=...                                 .. .                                            
                                   .-::-*%+=---::::.                     ..     .....    .                                        
                                    ..::-+*+++*=-:--.                ......    ............                                       
                                    ..:::-=**+++=---...      ..      .....     .......:..........                                 
                                  .. ...=.:=+**=++--::::::::....  .. .  .     .........::::........                               
                               ..::...::==:-:-**=+*++==-------......         ............:::...::...                              
                               :=-::.:---=++--=*=+++#*##+++=+-:::::.              ......:::.:::::::.                              
                              .:-=--=+##+=--=-===-===+==++*#+-:::...                  ..::...:::::..                              
                              .::-+=++=---:===-+==::--=-==++##==-:-:.                   .:::::::-:..                              
                              ---=+=+-::-=*####*=-:::--+*#-*##%##+-..                    .--::::...                               
                            ..==*+==-==*---+=+=-==--:--:=++*=##*++-..                  ...:::==::::                               
                           ..+**++===+-:::.:-:----=+-==--++*+=*#*--:..  .               .:-:-=---:.                               
                           .--+*=*++*+---:.....:---=+-**#*=++=--+=-:::::..     ..........:::-=---:.                               
                          .:::==+*%+#*=-::.....:-:-=+-+*%*-#**:=.:==-:..........::::...::------:::                                
                         ....:-=*%%*%#=:-::....::-+**--+%*+*+*=::-=---:::::::::==--::-:::==+-:::.                                 
                          ...::-+%%*#*+==--::::--+##--=*#*+==-=-:--*#=---::::::-==-+==-==--:: ...                                 
                          ....:-=#%*++=+*=--::---+#=:-*%*=--::-::-+-**+----::-=--=+--==-::::. ...                                 
                           ....::-*%+=*#**#**=-==---=###:::.....::.=#==---:------=++--:::......                                   
                           .....:=:+%*:+=---=-----*#%#=..      ...  .........:::::::..                                            
                            ... ..---=++#*#*+***#**=:..        .               ....                                               
                                 .:*+=:-----::::::...                                                                             
                               ..=*=:..:::.:::..                                                                                  
                                   =*........ ...                                                                                 
                                     :=+=:     .                                                                                  
                                      -  .     .                                                                                  
                                                                                                                                      
                """;
        FRAME[68] = """                                                                               
                                                               : .                                                                            
                                                .=  -.                                                                            
                                           .:-....=-::.                                                                           
                                      ....:=+++:+:+...:-      .                                                                   
                                      .:--+-::::-+-::-:-++.   .                                                                   
                                     .:---:-::::.:::=:-.-==-=-                                                                    
                                .....:--::==-::::--::::..---=:                                                                    
                               . ..:.::.--:.:-=.  ....:..::::==:                                                                  
                               ....::::-:.-=-              ..=::                                                                  
                                 :...:-.:=-:....                                                                                  
                              ....:..:::==+-::=-.                                                                                 
                                ...:::=+**=-:::....                               ..                                              
                                   .-:.-+%***+=-::::.                   ..     ...                                                
                                   ....:-**++*+=-:::.                ......    ...... .. .                                        
                                    ..:.:-+**+=*=---:..      ..       ..      ..  .......  .....                                  
                               ........:-+:-***=++=--::::::::...       .      .. ......::....... .                                
                             ..::::...:-+#*:==*++**++++--:---::.....              .......:..........                              
                              .--::::-==-=++-:-+++=*##**++++==:::.:..              .  ...:....:.....                              
                              :::---*###+-=--=-=-==-=++++**##=:::...                   :.....::::....                             
                              .:::=+**==--::=-*-=-::-=-*=++*%#+-:::-.                  ..:..:::::: .                              
                             .:-++--+--=-++*#*+++-=::+:+##*+*###+=-:.                   . ---::::..                               
                            ..=======-==-:-=+=--+=--:--==+++=--+==-:.                   ..:::--::...                              
                           .+=**+=+=++-:::..------+=:=---==+==-:--:...                  ..::::::::.                               
                           .:=+===*+==-::::...:----==-+**==++-::-=::......     ...... ....:::::::.                                
                          ..:-=-*##***=-::.:...::--=*:=*%#=**#-::----::....   ..::::....::-:--::...                               
                         ....:-=+%#*%#+::-:....:--+**--=#%+*=+-+:+*=-::::.....:---::--:::-:-:::..                                 
                           ...:-+%#*#*+=--:::::--+##+:-*%**+=-----=++=-:::...::---=---:=-:..: ...                                 
                           ....:=+%***++==---:::-*%+:=*%*=--::-::-=+=*=:::::::==-==-:--:...::  ..                                 
                           ....:-:###=####+++=--=+--=#%+-::.....:..=*=---:::--::--==::::..     .                                  
                            ....::-+##=-==--------+%%#-..      ..   .:.......:..:::..                                             
                            .....:-:=++*+==**+*##**+:..        .               .. .                                               
                               ....:----::----=-::..                                                                              
                                 ..:..:-:===:::...                                                                                
                                  ......-==.. ...                                                                                 
                                       .   :-==::.                                                                                
                                             ::.                                                                                  
                                                                                                                                                                                                                                                 
                """;
        FRAME[69] = """
                                                               . .-                                                                           
                                            ..     =:.-.                                                                          
                                          ....:-:-:.-*=::                                                                         
                                     .....:=--+++=::=...:-                                                                        
                                     .:-==---:-:.:==:.-:.::=    :                                                                 
                                     .:=--=::=---:.:::.:-:-=-..                                                                   
                               .. ...::--:==-::::::::::.:-:--:=+:                                                                 
                              .  ..:.:=.-=:..-=:.  ...::::.:.::#-                                                                 
                                 ...:.:-:.-+:           :...::.:*:                                                                
                                .:...:-.:====:-==.                                                                                
                              .......:-=+-=-:::..                                                                                 
                                 ..::::+#+==*-:::::.                                                                              
                                   ..:::+*#=+==-:::::                   ..                                                        
                                   ...::=+**=*==-::::.                         ...                                                
                                   ....:::+#*=++=---:. ...       .                . .....    ...                                  
                                 .:...::=.:=***++++--::::::.:....                ...............                                  
                              .-:.....::+#:-:=+%+=*+*+==-:----::.                 ...................                             
                             .:::::---==-=++*--++=*+##*++**+==:.....                  ..... ..:.... .                             
                              .:::-=*###+==-===-=====+=++####+:.....                  .:......::::..                              
                              .::-=+#+==--=-==*#--::--+*=+*+##+-:.:-:                  ..:..::....                                
                             .:==-:-===:-++*#+++=:--:---##+-::+*=:-:.                    .:::::::..:                              
                            ..-=--==--==-:--=---+=+-:--:=*+----==-::.                    ..:::-::...                              
                           :==+*+====+-:::.:::::--+=-+=--+===:::---...                   ..:::::..                                
                           :-=++=++*+=--:::.:.:----=+-+###++*=::-::::.....     ...........::::::..                                
                          ..::==**%*#++-::::...::--=*==#%%+*#=*.+----::....    .::..:....:-:--:.....                              
                         .....:=+%#+%#+-:-:....::-+*#-:*%#++++=+-=+=--:.......:::---:::::-:::...                                  
                           ...:-+%#+%#+=---:::::-+##=--#%*++=-------=-::.....:::---------:..:. .                                  
                            ...--*%*##*+==---:::-+#+--*%#==-::::::---==:::::::--=--:---::..::                                     
                           .....-=#%+++#*#+++=--=+:-+#%+-::........-=--:::::.:::----:::...                                        
                            ....::=+%%#+-==--=----+*%%-.        .    :.     .....:::                                              
                             ....:-.-=*#*#+==+***+*=:..        .              ..                                                  
                                ...:-::.:::::::-::.                                                                               
                                 ..:..:-:::::=-:.                                                                                 
                                   .........:*:..:+::-                                                                            
                                       .  ......--:                                                                               
                                                                                                                                  
                                                                                                                                     
                """;
        FRAME[70] = """
                                                                    ..                                                                        
                                            ..  ....  +  -                                                                        
                                        ....:::=-----:.*===                                                                       
                                      ..:::---==+#*-::-..:.-                                                                      
                                     :--==--:===-:.:==:.=:.:::                                                                    
                                     .-----::-=--:::=..--.-:-++-                                                                  
                              ..   .::-:-:==--..::::::::::.:-----+-                                                               
                              .  ...:-..==:..-=:....:--:.-:-.=::-=-                                                               
                                  .::.:-:.:+==..==.       ::.-:.-+=+:                                                             
                                ...:::-:++==:::=.               ..+..                                                             
                              .  ..::-:-*+=-::....                                                                                
                                  .. ::-+*==-++-:::::                                                                             
                                  ...::-*##**+=-:::..                                                                             
                                   ...::=*#*=++=-:::..                                                                            
                                   ....::-+#*=+*+=--:......                                                                       
                             ..........:-.:=*+**+++=---:..........                    .... .                                      
                             .::..:::.::+#::-==#+=**+=--------::.                     . ..      ....                              
                            ..:::---=====*+=*:-+*+*+#*+*#**+=-:...                    ...........                                 
                              .:::=*####*==--==--=====+*+++#**-.. .                   ...........:..                              
                              .:--==+*==----++**--::--++:::-=++-:..::.                  .........                                 
                             .:=-::-===--==*#+=++:-:----#+...:=+=-:..                    ..:::.....:                              
                             .==--==-===::-----+=+*::---=+==::--+-:..                    ..:::::...                               
                          .--=+*===--+-:::.:::::--==-+===+===*:+--::..                    ...:....                                
                           :-=++=++*+=--:::::::-::-==-+###++*+-=-::-::         .......  ..::::::. .                               
                          ...:-=**%##++=-::::..::--=*-=#%%+*+=+-+=--::.    .    ....... .:::::...  ..                             
                        ......:=*%#+%#*+-::.....:-**#=-*%%+++=-:------:.....  ..::::::::::::..                                    
                            ..:-+%#+###+---::::::+##+-=*%***+-::--:--:........:::::-:::::.....                                    
                            ...:-*%#*#*+=====:::-+#+:-#%#+=-::::.:::-:--:::::::--:-::-::.....                                     
                          .  ..::-##*+*##*+++=--=*::=##=-::.... ...:--:...::..:-::-::....                                         
                            ....::=+%##=-===----+#+++-+.. .     .   .-.      ....:::                                              
                               ..=::-=#%*#*+*++-.-::-.                        .  ...                                              
                                 .:::--.:::::::::-++    :-.                                                                       
                                  ..:.--::.::::::++...+%#.                                                                        
                                   .. ...........:..    ..                                                                        
                                           ......                                                                                 
                                                                                                                                  
                                                                                                                                      
                """;
        FRAME[71] = """
                                                                      = .                                                                     
                                          ....    ::..  - .:                                                                      
                                      ......::::-=-:---.-+*=:                                                                     
                                     ...:::----=====----:.-::                                                                     
                                     .::-=-=:+=--:::-=:=:+:. +:.                                                                  
                                     :==-=-::-=----:.-::=:.-::=*=                                                                 
                                  ...:-:::===:.--:::::::::.:-.:=+-:-.                                                             
                                  ..:-::=-::.-=:...:----::-::.::---=.                                                             
                                  ..: :-:.:=-=---=:-  .... ::..:::.=:.                                                            
                                ..:.:::.=#==:::::                -.:*:                                                            
                              ....::.:::-#==-==.....                                                                              
                                 ... ::-****====:::...                                                                            
                                  ....:=*##***+-::....                                                                            
                                   ...::=*#*+**=-:::..                                                                            
                                  ...:.::-+*#++**+--::.....                                                                       
                             ..........:-.-=*+*++++==---:.........                     ..                                         
                           ......::::.::=#::-++#+-**==--------:::                      .        ..                                
                             ..::---++===+++*--+*+++**+*##*++=:..                           ....                                  
                              ..::=######+=--==-===-=+-==-=+++:...                      ..........                                
                              .:::---+==---==***:::----:....-++::...::                   .........                                
                              --::::==---==+#=+++:-:::-=*+:::-+=-::..                    ..:.:......                              
                             .----==--++:::----==++:=---=+=---=+=-:..                      .:.... .                               
                          ::-==+=====+-:...:::::--==-========*-=--:::.                     ......                                 
                          .:--++=++*+=-:::::::::::--=-+###=+*=-=-:::::         .......   ..:.:..                                  
                          ...:==*##*%+==-:::...::--=*==%%%++====+=-:::.    .    ......  ..::::...                                 
                       .   ...:=*%#*#%##-:::....:-*#*=-*#%+++-:::-:::-:...     ..:::::..:....                                     
                            ..:-+%#+#%#+--:::::::+*#=--*%***=:::-::::::.... ..:::::::::::.....                                    
                           ....:=+%**#++===--:::-+#=:-#%#+=-:::::::::---::::::::::::::::.....                                     
                            . ..:=*%#+###+====--=*-:+#+=-::..:... ..-:-:...::.::::-:...                                           
                            ....:.==*##==-==----=*++---... .        .:.      .....:.                                              
                               ..-:.-+####***+##**+=-#.  .+-:                    ..                                               
                                ...:---:.::::::::::-=-  =#*.                                                                      
                                   .::::..:::--:::.:=:-.                                                                          
                                   ..  ............. .                                                                            
                                              .  .                                                                                
                                                                                                                                  
                                                                                                                                     
                """;
        FRAME[72] = """
                                                               ...      :                                                                     
                                        ..  .......::... :+ ..                                                                    
                                      .::...::::-=-=:-:--.+-++                                                                    
                                   . ..:::-::--==+==--=.--:=+::..                                                                 
                                    ..::----+:+-:..::--:+:+:...+.     .                                                           
                                    .:--=-=:--====-::::-:=-.:=::-=:                                                               
                            .    ...:--::-==-:::::::::::.:-:.:-.-+-:                                                              
                                 ...::.:=-:.:-=--:-+------:.:::-----:==                                                           
                                  ...::-:-**=::::-....::::..::.-+=+:-#.                                                           
                               ....:.::..=#=-::....           : ..::-:**                                                          
                                ...:.:::--*+=-+=..:...              ..:                                                           
                                 ..::::-***#+=-*::.....                                                                           
                                   .:-:-+%*+++=-::....                                                                            
                                   ...::-##++**+-:::... .                                                                         
                                  .....::-+#*+*+++=-::.....                                                                       
                            ... .......:-.:==#*=+++==-:-:....  ..                                                                 
                         ......::::::.:-=+--:=#*+*+*======--:::::.                                                                
                              ..:--=++====+*=:=**+=+***###*+==: .                                                                 
                              .:::=++=*##+=-===-=---=:.:.:-+=--.                             ....                                 
                              .:::::-+=-----==+*:-:---:..::-+++::.. ...                   ......  .                               
                             .::::::==--======++++::---**+--:##+-:..                     ..........                               
                             .:---+==---:::-----++=-:--==+==--+=--....                     .... .                                 
                         .:::-==-+=+=+-:......:::--=:==-+==--*=--:..:.                    ....                                    
                          .::-=+++*+=+--::::..:::::-=-+#%#=++==--:::::               .   .......                                  
                          ...:-=+*###*===-:.....::-=+-=#%#===----:::.:.            ....  .......                                  
                            ..:=*#%*#%##*-::.....-+#*--+%%+=-::::::::..        .....::.........                                   
                             .:-+%%*##*=-::::::::=+#=-=*%+*==:::::::..:..   ..:..::::::.....                                      
                             ..:=*%+#++==----:::-=*+--+=++=:........::::::..::.::::::......                                       
                             ...--#%#*#**+===---=*==#-:+=-::-=:.   .-::....::...:::.....                                          
                              ..:::*%#+=--*==----=*#%#=#-. -#=     ..::       ......                                              
                               .:----=*##+**=*+*#*+=::-#::=+:                    ..                                               
                                ...-+---:::::::::--:..:-:.                                                                        
                                   ...::...:-=-::::::...                                                                          
                                    ....    ..........                                                                            
                                                 .                                                                                
                                                                                                                                  
                                                                                                                                      
                """;
        FRAME[73] = """
                                                         ..     ....     . .                                                                  
                                      ..... ......::::...  = .-                                                                   
                                     .......::::-=--+--:::.=:=-                                                                   
                                   .....::--:--=+-++-==:--::+=--:..                                                               
                                    ..:::-=+:=-=:. .::===::-....-..                                                               
                            .       :--=-==::-====---::::--::=:::-=.                                                              
                                   .:----:==-:...::::::..:::--.=:+*=:                                                             
                                 ...:--:=-:.:-=----++=----:--::-:-:==-*-                                                          
                                  ..: ::::##=-:..::...:::::..::-..+.::=-                                                          
                                ..:.:--..-+*-::......  ...     ..:::.:+-+                                                         
                                 ..:.-::---*+=-=:.:...               .:=:                                                         
                                ..::.:::***#+==-::.....                                                                           
                                  .:.::=*%++*+=--:...                                                                             
                                   ...::=*#+***+=-::....                                                                          
                                  .....::-+#**+*===-:::...                                                                        
                           ... ........:-::-***=+++=-::--:...    .                                                                
                          .. ..:::--:.:--*::-=*+*+++======-----::.                                                                
                              ..::-+**++====#::=*+=+*++###+=--. ..                                                                
                              .:::----+##+==-=-=----....::-+=-:.                                                                  
                              .:.::::==-=----=+*--:-:---:::=*+=::..   .                   ......   .                              
                             .:::.::==-+=+--====+:=:-=-=*+=-:**=::...                     ..                                      
                             .::--===-==:::--:--===-:--=++=--==---....                     ..                                     
                         .:.:--=-=+==*=:.......::---:==-++=--+=--:....                    ..                                      
                          ..:--==+**+*-:::......:::-=-+#%#-===--:::...               .   .. ...                                   
                           ..:--+*#**#+=--::.....:=++==*%%+==--:::.....             ..    ..  .                                   
                            ..:=+%%*%%%*+=:......:+*#=-+%%+=-...::.::..         .....:........                                    
                             ..-+%%*%*+==-:::::::==*+-=*+++=--::::......     .......::......                                      
                              .:-+%+*++=-----:::--=+-+==++=::--:.....:::....:..............                                       
                             ..:-:###*#*++===-:-+*-:-*#*+-:.-#=     :.::.......:::.....  .                                        
                              ..::-+##+===+=-=-:-=+%#*--#-.-#-      ::.       ......                                              
                               .::=:-+*#%*+*++*##*+=-:::==:.                      .                                               
                               ...:-+--:::::::::::-::....                                                                         
                                   ..::....:-=--:::::...                                                                          
                                    ...     .::..... .                                                                            
                                                                                                                                  
                                                                                                                                  
                                                                                                                                      
                """;
        FRAME[74] = """
                                                     .    ...   ... ..    .                                                                   
                                     .........:..--:::::..  .. =                                                                  
                                    .......:::::=::--=::-..:.-:.-:...                                                             
                                  ......:------=======---=-::-+*--:..                                                             
                                   ...::---+-+=-:....-*#*-::-..:.-:.                                                              
                                   .:-==-=-::-====-----:::=:-:--.:-:.                                                             
                                  ..:==-::==-:....:::::..:::-=..::=+*-                                                            
                                 ...:--.=-::.:=:----====-::::::.::::=--==                                                         
                                  ..- :::.-**::.:..:..::::.::::.:::---:-:                                                         
                                ...::-:..--*-::..............   ...:.:::+-                                                        
                                ...-.-::--*+++=:-:.  ..              :.::+-                                                       
                                .::: ::-***#+===-:..                                                                              
                                 .::.::=*%*+*+==-:...                                                                             
                                  ..:.::-##*+**+=-:::....                                                                         
                                 .....:::==#**++=-----::..                                                                        
                              ........::-.:-+**=++==---::::..     .                                                               
                             ..:::-=:::--=::-=*++**++++====---:::.                                                                
                              .:::---+*++===*.-=*+-=:=+*##+=-:....                                                                
                              ...:::::-=**==-=----...:::-==+-:::.                                                                 
                               ...:::--=-----=++--:--=====:++=--::..                                                              
                             .::::::--:--+----===:-:-+-**==--==-::...                                                             
                             .::--===-==:::--::-==+:---+++=-----::....                                                            
                         ....:--==++-*+-:.......:---:==-++=--=--::....                                                            
                           .:-:==+**=#=-:.......::--=-+*#*===-:::.:...                                                            
                            .::-+*%*#**=----:....:++*==#%%=---:::.....               .                                            
                            ..:=+##*####+==:.....-=*#--+#*+-:..:.......          .....     .                                      
                             ..-+%%*#===--::..:::-=*+-+--+=--+=-:........    ..........                                           
                              .:=*%++===---::::::=++:++#+#=:.*%*- .. .:...............                                            
                            ....-:#%#+#++====-:-=+--=%%+:#::=:.    ..................                                             
                             ..:-:-+##+-===+---:--+###---:--..      :..           ..                                              
                              ..::=--=*%%+**++*##++=-::::.          ..                                                            
                              ....-==--:.:::.::::--::..:.                                                                         
                                  ...::....:----::::...                                                                           
                                    ...    .:--:.... .                                                                            
                                            ....                                                                                  
                                                                                                                                  
                                                                                                                                      
                """;
        FRAME[75] = """
                                                 ... .    ... ........     : .                                                                
                                         ........:::::::...  .: - .                                                               
                                   .. .....::-:--:--::----...==:-=:...                                                            
                                   .....::-------====*=-----::+++=-...                                                            
                                   ...::--=-==-+:... :*=#*::::.::.=.                                                              
                                   .:-=+----:-=-==-----:::-=-:-=...-:.    :                                                       
                                  .:---=:-==-:....:::::..::-.=:::-:-=+:   :                                                       
                                  ..::-:=-:-+:=----:-+==--:::.:.:..=:=--:.                                                        
                                  .::.:-:..==-:::...:.:::::.::.-:-:.---+-=:                                                       
                                ...:.:-..-==#-::::......::.......:..::::+*:                                                       
                                ..-::-::--**=--==-.   .              .::-==-                                                      
                                .:::.:::*#*#+===+:..                    . .                                                       
                                .::::::-*%**#+===::..                                                                             
                                 ..:-:::=*#****+--:::....                                                                         
                                .....::.:-+*#+=+=---:-::.:.                                                                       
                             ...:::....::-:-***+++==---::::..                                                                     
                             ..:::-=-::::==:-++#+=**+++==++-::.:::                                                                
                              .::::--=++++++::-++=-:--+*##=--:: .                                                                 
                               ..::::::-+*+=+=-=:...::::====-:::.                                                                 
                               ....::-==-=---==:-:---===+--===-:::..                                                              
                              ::::::*--:==--:=--=+:-:-==#==-----::..                                                              
                             .:::======-::::-::--=--=-==+-=-:--:::.                                                               
                         .. .::--=+==+#=:..:....::---===++=--=--::..                                                              
                            .::-++#*+++-:........:----+###----::....                                                              
                            .::-*##**##+=----.....=+#=-%%%+--::...                                                                
                            ..:=*%#*%#*#+---.....:-*#--+++=-:.=:-..... .         ..                                               
                              .-+%#+*=---:::....:-=**-=*+=+-:-#*=..  ....    .. ...  .                                            
                              .:=+%**==----::::.:-==:-#%*+#:.== .    .........  .....                                             
                              ..:=###+#++===---==-:-*%%+=-=+-..    ....  ...  ......                                              
                             ..:-:=+%#*=-+-=------+#%#=--::...     ..                                                             
                              ...-=:=+####**#+##**+=:::::.         ...                                                            
                              ...:--=--.::::::::---:::...                                                                         
                               .  ...::....::---::::..                                                                            
                                    .....  .:--:.....                                                                             
                                            ..::                                                                                  
                                                                                                                                  
                                                                                                                                      
                """;
        FRAME[76] = """
                                                  .  ..    . ..::...:..                                                                       
                                         . ....:::::::::...    : ::.:..                                                           
                                   . ....::::------::---::+...:*--*:::.                                                           
                                  ....::::--==--====+==--=--==-+++-=..                                                            
                                   ..::::---==-=:.....-#:--=:--:---=.                                                             
                                   .:-=+----:-=-=-==---=-::=-=:+-...=:.                                                           
                                 .::-=--::==-:.....:::::::::=:==.:-::-+:                                                          
                                  .::.=:=-:.-*=---*##+=+=-:::::-:::=:-+=:  .                                                      
                                  .:-:--:..--+-::::..::--:::::::::.-+--=:-==                                                      
                                 ..:.:-..--==*-::::.....:::.......:=..=.:-#:                                                      
                                 .-::-..--*#*=-----      ..        . ..:.=+=*                                                     
                               .::::.::-*#*#*===--.                      :::.                                                     
                               ::::::::-*%**#*=--:...                                                                             
                                ..::::.:=*#*+**+--::.....                                                                         
                               ......:-.:-+*#+=+=-----::.....                                                                     
                              ..::::...::-:-##*=+===---:::...                                                                     
                            .....:::--:::-+:-++**=*++++=+*=-::....                                                                
                              ..::::::=-==++:::=-:::-*##**=--::.                                                                  
                               .....:::-=**+==-...::---=+==-:::::.                                                                
                               .....:----=-=--=--:----+#+------:::                                                                
                               ..:-:+--:==-::--==+=:--==#=---:-:::.                                                               
                              .::-=====-:::::::--=-:=---*=----::::.                                                               
                             .::-=+==+#*-:::....:::--+==++=----::...                                                              
                             .::++#*=*==-:........:-=-+##+=---+--.                                                                
                             ..:*%%+###*==--::....-*#==#%+=--:*-:.                                                                
                             ..=*%#**+==+=-:-:...:-+#--*===-:.*%-.      .                                                         
                              .-+%#+*=--:::......:-+*-=*%*#+-==:.      .    ..                                                    
                              .:=+%*=---:::::::.:--=:-#%*#+-*:.       ... .                                                       
                               :-+###=*===--=---==:-*#%*--::..     ...     .  ..                                                  
                             ..:::=+##*-====------+#%#---:..                                                                      
                              ..:-+:=+####***=*#***=-::::.                                                                        
                                 .:-=-:::::::::.---::::.            .                                                             
                               ......:::...::-=--:::..                                                                            
                                    .     ..::::.....                                                                             
                                           ..:::.                                                                                 
                                            ...                                                                                   
                                                                                                                                      
                """;
        FRAME[77] = """
                                                  .  ..    . ..::...:..                                                                       
                                         . ....:::::::::...    : ::.:..                                                           
                                   . ....::::------::---::+...:*--*:::.                                                           
                                  ....::::--==--====+==--=--==-+++-=..                                                            
                                   ..::::---==-=:.....-#:--=:--:---=.                                                             
                                   .:-=+----:-=-=-==---=-::=-=:+-...=:.                                                           
                                 .::-=--::==-:.....:::::::::=:==.:-::-+:                                                          
                                  .::.=:=-:.-*=---*##+=+=-:::::-:::=:-+=:  .                                                      
                                  .:-:--:..--+-::::..::--:::::::::.-+--=:-==                                                      
                                 ..:.:-..--==*-::::.....:::.......:=..=.:-#:                                                      
                                 .-::-..--*#*=-----      ..        . ..:.=+=*                                                     
                               .::::.::-*#*#*===--.                      :::.                                                     
                               ::::::::-*%**#*=--:...                                                                             
                                ..::::.:=*#*+**+--::.....                                                                         
                               ......:-.:-+*#+=+=-----::.....                                                                     
                              ..::::...::-:-##*=+===---:::...                                                                     
                            .....:::--:::-+:-++**=*++++=+*=-::....                                                                
                              ..::::::=-==++:::=-:::-*##**=--::.                                                                  
                               .....:::-=**+==-...::---=+==-:::::.                                                                
                               .....:----=-=--=--:----+#+------:::                                                                
                               ..:-:+--:==-::--==+=:--==#=---:-:::.                                                               
                              .::-=====-:::::::--=-:=---*=----::::.                                                               
                             .::-=+==+#*-:::....:::--+==++=----::...                                                              
                             .::++#*=*==-:........:-=-+##+=---+--.                                                                
                             ..:*%%+###*==--::....-*#==#%+=--:*-:.                                                                
                             ..=*%#**+==+=-:-:...:-+#--*===-:.*%-.      .                                                         
                              .-+%#+*=--:::......:-+*-=*%*#+-==:.      .    ..                                                    
                              .:=+%*=---:::::::.:--=:-#%*#+-*:.       ... .                                                       
                               :-+###=*===--=---==:-*#%*--::..     ...     .  ..                                                  
                             ..:::=+##*-====------+#%#---:..                                                                      
                              ..:-+:=+####***=*#***=-::::.                                                                        
                                 .:-=-:::::::::.---::::.            .                                                             
                               ......:::...::-=--:::..                                                                            
                                    .     ..::::.....                                                                             
                                           ..:::.                                                                                 
                                            ...                                                                                   
                                                                                                                                     
                """;
        FRAME[78] = """
                                                      . .. .........::.         ..-.                                                          
                                         ...:::::::....::..     ..::-:::.                                                         
                                  ..::::::::--::-:--::::::::---:--*:::...                                                         
                                  ..:::::----====*+++------++=--==*-+*.                                                           
                                   .:--::---==--.... ..::+==--=--::+===                                                           
                                 .:::-=-:-----=====-=----::++*--:-:.::-:                                                          
                                .:::---=:-==-:...:...:::::=::-:--::-:..--:                                                        
                                 .:::.-:=-:..:=+=-=+++=++-::::::::-..-:-==-.. .                                                   
                                 ..:::-::..=+=-=:::.:---:=--:-::-::::..-:---=..                                                   
                                  .::-:..-===--+=--+-..::::.....::.::::.:-:-==:                                                   
                                .-::.-::-=*##=---:=-.   ..           ::..::.-%-                                                   
                             .....::.::-***#*=---:..       .             ..::.**.                                                 
                             .:.....:::=*%**%#==-:.  .                        .                                                   
                                .:::::.:-##++*+=--..... ....                                                                      
                               ...::::-.:==#*+++=-----:.... .                                                                     
                                 ..:::.::::-***=++=--=-:::..                                                                      
                             ......:--::-=*:==***+++++++*=:::...                                                                  
                               .......::-==*.:::-==*%%##+=-:::...                                                                 
                                .....:-:-+-::::::--=-=+#+=-::::...                                                                
                                 .:..:-==:::::-------+*#*=-::-::...                                                               
                                .:--=--=-=:::--==+=--+--*=::==-::..                                                               
                               ..:-====++::..:::-=-::--=+=-----+:..                                                               
                              ..-=*+=++=*=::....:::-:=-=+++---*..                                                                 
                              .:=+#*=*=----.......:==-=#+#*=--=..                                                                 
                            ...:+%%+***+---::::...:+#--==%+=+=-..                                                                 
                             ..=*%%*+=-----:.::...-=#--*%%+-::::.                                                                 
                             ..-*%#+=--::::......:-++-=*%*+=-::.                                                                  
                              :-=*%++--:::.:::...-==:=#%*#*=:.                                                                    
                             ..:--*%%==----==-:.-=--=#%+-::.                                                                      
                            ....:::*##+=====------=###---:.                                                                       
                             ....:+:=+*#%***++*##*+=-:::..                                                                        
                                 .::==-:::::::::--::.::.                                                                          
                                  ......:::.:-===-:..                                                                             
                                         . ...:-:.                                                                                
                                           ......                                                                                 
                                           .....                                                                                  
                                          .                                                                                           
                """;
        FRAME[79] = """
                                                      ..............::.         ......                                                        
                                         ...:::::.:....::..      .:::-..:                                                         
                                   .:::::::::--::--=:::::::--:-:--:-=::. .                                                        
                                  ..:::::::--=++=+++=--=---+++=*==-++--.                                                          
                                   .:--::-:-==--..  ...::=*===+---:-+-**:    .                                                    
                                 :-::---------====-=-----::-+*+=::::::+:-.     .                                                  
                                .::::---:-=+-:.:-.:..::.:::--::.--:-+.. --.                                                       
                                 ...:.-:=-:.::--===+=++++=-:::::-:=*:.-::-++.                                                     
                                ....::-::..=*=--=:::--=--+=-:::::::::::-..*+-.                                                    
                                 ..:.-:..:+==-::=+-==-:::::.......::..::==-::-+=                                                  
                                .:::.-.:-=*#*=---:.--   ..           .:-:.+::-#-.                                                 
                              ..::::.::-+**#*+---:..                   ....:.::-*=                                                
                            .:.......:.=*%*+%%+=-:..        .                ::*-:                                                
                               ..:..::::-##+=*+=-::.::.  ....                                                                     
                               ....:::-.:-=#*++=------:..                                                                         
                                ...::::=:-:-=**=++====-:::..                                                                      
                               .....:----=+:-=*****++++++=:.....                                                                  
                                  ....::--=+:-:=*+=*#%%#=--:.....                                                                 
                                  ...:::-:::-:::-----*##+=-::::....                                                               
                                 .:.:::--:.:--==-----++#*=:-*=::...                                                               
                                 .:--===-+:::--=+*-:-+=*#=::=--::..                                                               
                                .:-=====*+:.::::-------++==-:+::..                                                                
                               ..=*+===-=+::....:::----#*+*=:=:..                                                                 
                               :-+#+=*=-:--.......:=+-+###====:..                                                                 
                             ..-+#%+++++-:::..::..:+#-=#%%=-::.::.                                                                
                             ..=*%%*+--::::::.:...:=#--+%#+-::::.                                                                 
                             ..-*%#+=-:::.........:++-=*%*+=-:.:                                                                  
                             .:-=*%+--::::::::...-==-=*%*#*=:.                                                                    
                              .:=-#%#==-:--=--::==--=#%+-:..                                                                      
                              ..::-+%#+-======----+%%#---:.                                                                       
                             ....:=--=*%#*#*+*+##+==-::...                                                                        
                                .::---=-::::::::--::.::.                                                                          
                                   .....:::...++=-:..                                                                             
                                        .  ...:-:.                                                                                
                                            ...:.                                                                                 
                                           .....                                                                                  
                                          ..                                                                                          
                """;
        FRAME[80] = """
                                                      ..:::..... ......         .:.....                                                       
                                  ..... ....:::::.......::.       .:.....                                                         
                                   .::::.:..:--:====:::::::::::::--::::.-  .                                                      
                                  ..::::::::-++*=**+==--=--==---=+-:..*..=                                                        
                                   .:--::::-*-=:.. ....::-+=====----+-=+*=.                                                       
                              ..:::::---------=====------::..++:-=--:::==-+.                                                      
                                ...::-=:--=+-:...:-.::.::::=----:-==-+-...:=.     .                                               
                                ....::.-=-:. :---::===++++-::--::--:-:.-:.-::-                                                    
                                 ...-:-::..=*=---=--=---=-----::::::-::.:--#===   .                                               
                                ..:::::..:=++=--::-+++=::::::::....:-:-::.::.=:==:                                                
                               ..:::.-..-=*#*=--::..==....          ::.:-::.--::-.:                                               
                               .:..:.::-+**%#*=-:.                       ::.:-:::*..                                              
                           .:. ......::=*%*+%%#=-..                          :-:-:**+                                             
                              .......:::-#%++#*=--:..:.                         ....                                              
                                 ...::-::-=#**+=--:---:...                                                                        
                                 ...::-*=::-=#*=+=====--:::.                                                                      
                                   ..::--==---******=++++-:..:..                                                                  
                                    ..::-==+=:-+**-*%%#*=--:.....                                                                 
                                 ....::::-=------=-==###*+=-=::...                                                                
                                 ::::::::-::--+*:--=-+#**+:=.:::...                                                               
                                 .:::::.-*:::::-++-:-*=*+=:=::::...                                                               
                                .=====-*+*+..:::----=*==**+=:-:..                                                                 
                                .-*+*=---+-.....:::--=++*+*=:-:..                                                                 
                               :-+#*=*--::-:......-==-+*#*+=:::..                                                                 
                               -=%%++==--::::.....:+#==#%%+-::...                                                                 
                             ..-#%#*=-:::::-:.....:=#--+%#*=-:::.                                                                 
                            ..:-*%#+=-:::.........:+=-=*%**=::..                                                                  
                            ...:-*%+--::::::::...:==-=*%**+=-:.                                                                   
                              .:--###--:::-=-:::-*-=-##=-.:                                                                       
                              .:::-+%#+==-===----=+#%#--=:.                                                                       
                             .....-:-=*##***=*+*#*+=-::...                                                                        
                                ..::--=:::::::::--::::.                                                                           
                                  .......:::..-#=-:..                                                                             
                                              .--..                                                                               
                                              .:.                                                                                 
                                           .....                                                                                  
                                         ..                                                                                           
                """;
        FRAME[81] = """
                                                      ...::.. .   ....          ......    .                                                   
                                  .....  ....::::.......::.        :.....    :                                                    
                                   .::::.:.::--:====:::::::-:::::--::.:. .:                                                       
                                   .::::::::-++===++=---=--==---==--:.. =..:                                                      
                                   .::::::-=:=-:.... ...:--===---=+=+--+--=+                                                      
                             ..:::::::----:::=====--------:...:++=+=---:-*-+-                                                     
                                 ..::--::-==-:.....-:.:.:---=---=+-==:::.::.*-                                                    
                                ....--:-=-.:.:--=+-::=+===--::::--:.-:::+- ..--     :                                             
                                  ....--:.::++-::--=*=---------:::-:---#-.=:: +==                                                 
                                 .:..:-..-=++---::::=----::::+.....:----::.::.--==..                                              
                                ..:..-:.-=*#*=--:... :=::....      ...--=:.:---:--:-                                              
                               ...::.::-+#+%#*=-:..   .                  ..::.=..+#=-                                             
                          ...   .....::-*%**%##+-..                         :.::::=:##-                                           
                              .......:::-*#*=#*=-:::.:.                          .:--:.                                           
                                 ....:::::*##=+---::::....                                                                        
                                   ..:-#+=:-****+====---::.                                                                       
                                   ...:-=**::=+#+=#*=+++=--...                                                                    
                                    .:.:-==++-:=*+#=####===-.. ..                                                                 
                                 ....::--=**++--==+-*###*==::::...  .                                                             
                                 ..:::---==---++--==#=+===*::::...                                                                
                                  .::::--=:::::-+---+==*=-::::....                                                                
                                 ---.:-*++=:.:::-----=+=+=--:::.                                                                  
                                .-++=---:==:.....::--=+=+++=:::.                                                                  
                               :=##+=+-::::..   ..-==-+###+=-:....                                                                
                               -*%%=+==-:::::.....:=#=-#%#=-:::..                                                                 
                              .-*%#+=-::::::::....:=#=-*%##*-:...                                                                 
                            ..:-*%#+=-:::.........:=+-=*%#*-:...                                                                  
                            ...:-*%+--:::::::....-==--#%**=--::                                                                   
                              ..--#%*=-:::-:-:::-+--*%%+-..                                                                       
                              :..:-=#**=---==-----+#%*-:-:.                                                                       
                                 .---+####**#+##**+=-::...                                                                        
                                ...::--:::::::::--::::                                                                            
                                  ......:::::.:#=::..                                                                             
                                              .--:                                                                                
                                              .:.                                                                                 
                                               .                                                                                  
                                                                                                                                      
                """;
        FRAME[82] = """
                                                      ...:...  .. ....          ....  .       .                                               
                                 .....   ....:::.......::::..      .:....        .                                                
                                   ..::....::--:--+-::::::::::::::-:....    :                                                     
                                   ...:..::-=+=+=++==+++*+=-=--===-:.....   :+                                                    
                                  ..:::::-=-*-=: ...  ...--===----=--::*:. =- -                                                   
                             ...:.::::-----:-======-------:....:=======---==+=*.                                                  
                                  .:::-:--==-:.......:..:------::.:+==----::-+++:                                                 
                                  .---.:+-..:::-=++===-:-=:::::--=--=::=-=-:. .+-:    .                                           
                                ...:::--:.::++--::--+=:-------=-:---:::-=-.:= .::=:  :                                            
                                 ..:::-..-+++=--:....:+=-:::::.....::--::::..---**--.                                             
                                 ..::-.:-=*#*=-::..   ..:=-:..   .......:-=--::.::-:=.                                            
                              ....:-::::=*#%%#+-:.     ..=-.             :-:::.-..----:                                           
                                ...:=-:-*%#*%##*-..                         .:-:.::..-+#.                                         
                                  ...::.-#%*=#**-:::.:.                       ...:-::-=+=                                         
                                  ...:-:::=#*=*--::::::...                          ...                                           
                                   .::-#+::=***+===-=+===:.                                                                       
                                   ..:--*#*-:==%==**+===-::.                                                                      
                                   .....:--==-:++=***#**==::....                                                                  
                                 ....:::-=*##*+:-=+-*****+=::...                                                                  
                                 ...::-=+==-:-**--*++-##+=-:.......                                                               
                                  --=++++=-:.::---=---=*+-::......                                                                
                                 -+*=*+-:=-:::::::---=-++=--::..                                                                  
                                .:++**-:::== ....:---===+*+=::...                                                                 
                              .--:==+=-::::..  ..:--=-=##*+=-:....                                                                
                              .==%#*+-:::.:.......-=#+-#%%+--::.                                                                  
                             .:=*%#*--:....:.:....:=#-:*%*#*+=:..                                                                 
                           . .:-+%*=-:::...... ...:=+--#%+*-....                                                                  
                              .:-#%=---:::......:-==-:*%*+=::::..                                                                 
                             ...:-##-=-:::::.---=+-:-#%#-..                                                                       
                             ....::#%#==--=====--=+*%+-:-:                                                                        
                                 .--:-##*%#*******=---:..:                                                                        
                                 .:::--=:::::::-=-:::..                                                                           
                                    ......:::::*=:...                                                                             
                                             ..--.                                                                                
                                              .:.                                                                                 
                                              ..                                                                                  
                                                                                                                                      
                """;
        FRAME[83] = """
                                                        ....      ....          ...                                                           
                                 .....    ...::::.........:..      .:....            .                                            
                                  ...........-----=-::::::::::::::::....      .     .                                             
                                  .......::-==========+***+-=----=-:..::.     .-                                                  
                                 ....::.-==-+-=..... ...:-===----==--:::::.  :-=:                                                 
                               ......:-:--=:--====-===----:....:-========-*::====.    .                                           
                               . ...::-:--==-:.......::::-=-=--::...-=*===-::--*+=    .                                           
                                 ..:--.:=-.:.::==++==*+=---:::::==:=----==+--...+==.                                              
                                 ..::.--:..-*+--::::=*==:.----=----:-=--.:..::. .-+-:.                                            
                                 ..:.:-..-=++=-:....::---=::::... .:::::--.--:::===+--                                            
                                 ..::-::-=***+-:..    ....-=-+=-. :. ....--==--::--:=+:                                           
                              .....::::-+**%##+=:.    ..   ==::           .:-..::=.::.--.                                         
                                  .---:=*%**%##*=..                        .:...:.=...-*+.                                        
                                   .::::-*#*+#*+=::-+-.                       . :::.::=*=:                                        
                                  ....:-::***=+=-::=+-....                          .-:.                                          
                                    .:-#*::-**++=-=-=+---:.                                                                       
                                   ...-=**+-:++#*##%**=+:-:.                                                                      
                                   .....:--=+-:-+=**%##+==:....                                                                   
                                 ....:::-=**#*+-=++-*#*##+=-....                                                                  
                                  ...:-:++=-::++-=====##=--:.......                                                               
                                  .:-+==++--:.:----=-==#+-::....                                                                  
                                :=-#%*=::--:::::::-:=-=++=-:::.                                                                   
                               :###+#-:::::-.....:----++++=-::..                                                                  
                              .-*%%*=-::.:::   ...--=:+*##+=-::.. .                                                               
                              .:=#%*--:...........-=#*=#%#+=-::.                                                                  
                            ..:=-=++-:::.:........-=#=-+%##*=-:..                                                                 
                             .::+%+--:::.....  ...:=+-=+%*+-...                                                                   
                              ..-*#-:--:..:.....:-==--#%+=-:.::...                                                                
                            ... .:##==-.:::..---=+-:+%%+:..                                                                       
                            ..   .:*%#+----=+++---=#%+-.::                                                                        
                                 .:---*#*#***+##**+=-=:..:                                                                        
                                 .:-------:::.:-=--:.                                                                             
                                    ........:::+-:...                                                                             
                                    .        ..--.                                                                                
                                              .:.                                                                                 
                                             ..                                                                                   
                                                                                                                                      
                """;
        FRAME[84] = """
                                                        ....      ....           ...                                                          
                                 ....     ....::..... ........  .  .:...                :                                         
                                 ...........:-:::-----:-=--::::..:::....       :                                                  
                                 ..  ...:::-=-=-=-====+###*=----:--::.::.       =                                                 
                               .......::====:+-..:.:.. ..---=----=---::::::.    +:                                                
                               ......:::--:-:===++=-------:....=-====+----::..-.=-.    .                                          
                                  .::::::-==-:......::..::-=---::...:==+==-:-:---=-=                                              
                                ...::-::=::.:::--+++++=-=--::::-==---.:--==--::::---+.                                            
                                 ..:::--:..-**=-::.:=+=-==------:=-:----=:--=-:.-**=:-=                                           
                                ...:.:-..-+++=-::...::-=--::::.:...:-==-----:--+:*-*==-.                                          
                                  .:.:::-=*#*=--...   .....:-+-=.    ....:-----=-+-----==:                                        
                                 ..:.::-+**##**++:.   ...      -=--     .:.:--.-..:::--=#-                                        
                                 :+-:::-*%#*###*+=..            =:-.        .:.-.-=:+-===                                         
                                   .::..-*%++##+=+:...          :            . ..:=-=++=                                          
                                    ..----=#=+**=-:::....                         .-==::                                          
                                    .:-**+:-=+*+=------:::.                        ..                                             
                                    .:-**++::-+#=**++=-=:::.                                                                      
                                  .....::--++--=++=*%##*==-:...                                                                   
                                 ....:::-+++**=-=++=+#**#*=-....                                                                  
                                  ...--:----::==--+-++#*=--:.....                                                                 
                                   .-++==---:.:----+--++=--...                                                                    
                                 .-=++=::::::..:::-:-=-++=-:.:.                                                                   
                              .:-=**-:::::::. . :::--=-=++--::..                                                                  
                             ..-+%#=:::...:::  ..::-=-=*#*=--::                                                                   
                              +-*#**:::...........-+#=-*%#+=-:+.                                                                  
                             .#%%#*==-:...........-=#+-*%#+*+-:..                                                                 
                             .++*%==:::-:...    ..:=+-=#%+--:...                                                                  
                              :+%%**::-:......::--==:-#%*=:: .....                                                                
                                ::=*=-:..:...:-=-=-:=#%#:..                                                                       
                                 ::=#*=-:::=+++--==*%+=.::                                                                        
                                ..:::=**##****+**=-----..:                                                                        
                                  .:::---+++=--=---:..                                                                            
                                  .:::::......:-:::.                                                                              
                                              :-:.                                                                                
                                             ....                                                                                 
                                             ..                                                                                   
                                                                                                                                      
                """;
        FRAME[85] = """
                                                        ....      .  .           . .                  -                                       
                                 ...       ...:...:....:.... .      .. .                                                          
                                       .....:-:::-------=+=-:::...::...       .-                                                  
                                      ..:::--=----=-==+##**=---::-:::...:..    .-        .                                        
                               ........:===-+=-..:..:....:=-=----==--:::::..    =.      .                                         
                                 .....:-=-=:-===++==-----:::....=====+==--::..:-==.  ..                                           
                                  .:::::--==-:......:...::------:...:-=-=--:.::==:+--.                                            
                                 ...::.:=::.:.--:=*+#=-=---::::-==-=-.:------:::-==--.                                            
                                 ..::::-:..=*+--::..==-===------=-::==-=-:--:::------:--                                          
                                 ..:-::..-+**=::::..::-==-::::..:-:::=-------:=++*:==--:.                                         
                                  :-:::.--*#+=--:..    ...::::.-:.. .. .:-:-=--===+---+#: :                                       
                                  .--:::=++***=+=:    ... .     :*.       .:::=:.::-+-+#=.                                        
                                .:=:::::*%+**#+++-:.               -=-:    =......-=*++=--                                        
                                  .:::-.-+++*%#*=--:.              -:.-      ..-::+**=                                            
                                    ..-*::=#**=-:--::.....          .          :::+---                                            
                                    ..-*#::-+*+==------::..                     --..                                              
                                   .::-*+===:-*#+++++==---:.                                                                      
                                 .....:-:-=++--=**=*%##+-=-::                                                                     
                                  ...:::-==+**=:=+==+#****=:....                                                                  
                                   .:---:--:::==--=-==#*=--.....                                                                  
                                   .:=+*=---:.:---:+==++=:::.. .                                                                  
                                ..:-==-::.:::....:::=-==+=-:.:.                                                                   
                              .--=*+::::::::.  .:::--===++--:-:.                                                                  
                             .:=++---.....:::   :::-=-*+#+=--::.                                                                  
                             .-=+##=::......... ..:=**=#%#++=:-:                                                                  
                             :--=#*==-:........  .:=#--+##-++=:..                                                                 
                              .:==:::::-:...  ....:=+-=#%*-:...                                                                   
                             ..-*+=-::-:......:---==--*%+-:.. ...                                                                 
                           .   #=%##==......:----==-=%#+-:.                                                                       
                                #+##%##--::-*+==--*%%+=:..                                                                        
                                .:+**==**+===*==+====--:.:                                                                        
                                  .:::::-**++-===-:::.                                                                            
                                  .:::........::::..                                                                              
                                    ....      ::..                                                                                
                                              ...                                                                                 
                                                                                                                                  
                                                                                                                                     
                """;
        FRAME[86] = """
                                                        ....      .  .           . .                  -                                       
                                 ...       ...:...:....:.... .      .. .                                                          
                                       .....:-:::-------=+=-:::...::...       .-                                                  
                                      ..:::--=----=-==+##**=---::-:::...:..    .-        .                                        
                               ........:===-+=-..:..:....:=-=----==--:::::..    =.      .                                         
                                 .....:-=-=:-===++==-----:::....=====+==--::..:-==.  ..                                           
                                  .:::::--==-:......:...::------:...:-=-=--:.::==:+--.                                            
                                 ...::.:=::.:.--:=*+#=-=---::::-==-=-.:------:::-==--.                                            
                                 ..::::-:..=*+--::..==-===------=-::==-=-:--:::------:--                                          
                                 ..:-::..-+**=::::..::-==-::::..:-:::=-------:=++*:==--:.                                         
                                  :-:::.--*#+=--:..    ...::::.-:.. .. .:-:-=--===+---+#: :                                       
                                  .--:::=++***=+=:    ... .     :*.       .:::=:.::-+-+#=.                                        
                                .:=:::::*%+**#+++-:.               -=-:    =......-=*++=--                                        
                                  .:::-.-+++*%#*=--:.              -:.-      ..-::+**=                                            
                                    ..-*::=#**=-:--::.....          .          :::+---                                            
                                    ..-*#::-+*+==------::..                     --..                                              
                                   .::-*+===:-*#+++++==---:.                                                                      
                                 .....:-:-=++--=**=*%##+-=-::                                                                     
                                  ...:::-==+**=:=+==+#****=:....                                                                  
                                   .:---:--:::==--=-==#*=--.....                                                                  
                                   .:=+*=---:.:---:+==++=:::.. .                                                                  
                                ..:-==-::.:::....:::=-==+=-:.:.                                                                   
                              .--=*+::::::::.  .:::--===++--:-:.                                                                  
                             .:=++---.....:::   :::-=-*+#+=--::.                                                                  
                             .-=+##=::......... ..:=**=#%#++=:-:                                                                  
                             :--=#*==-:........  .:=#--+##-++=:..                                                                 
                              .:==:::::-:...  ....:=+-=#%*-:...                                                                   
                             ..-*+=-::-:......:---==--*%+-:.. ...                                                                 
                           .   #=%##==......:----==-=%#+-:.                                                                       
                                #+##%##--::-*+==--*%%+=:..                                                                        
                                .:+**==**+===*==+====--:.:                                                                        
                                  .:::::-**++-===-:::.                                                                            
                                  .:::........::::..                                                                              
                                    ....      ::..                                                                                
                                              ...                                                                                 
                                                                                                                                  
                                                                                                                                      
                """;
        FRAME[87] = """
                                                         .       .....                          .                                             
                                            ..:...:::::--==:...      .             .                                              
                                           .::..:::::--==++=-:......::.....       -                                               
                                      ...::-----=======+###+--:::-:::=-..:..     .                                                
                                     .:---=-+-=:..:... ..---=---------+:*::::  .                                                  
                                    .::---=--==+=====----::::...-===+-*-=-:::-..                                                  
                                 ....:-:--==-:......:..::-=----=:..=-=*=+=----...--.                                              
                                 ...::::=-:..:-:::-+#+---:--:::-=--=**:+%---==--*-::..                                            
                                  ..--:-:..==-:=-=+-----====---:-::-=-=#------=-==--::.                                           
                                  .-+::.+#=*+=-::....::---:::::::::-:--=+=+---+*++-====-                                          
                                 .:--::.-##*+=---:.    ...::...=:+***---=###++#*-+===-=--                                         
                                  .:+-::+=+===+==-:.     ...  .::-*--=-#=++**%%%#+=====::=-:                                      
                                ...:--.-+%****+===-:       .:=:-+*=+=+%-=##*++**%##==-... - --.                                   
                                 ::...::-*#+*#**+=::: .     ::::*-....::+-==+=+-:....                                             
                                   ...-*::+#*+=-:::::.. ..  .::.:....--+*=+**+++.                                                 
                                    ..-#+:--+#++-:---::.:.   .   :*---==-.-.  --::                                                
                                   ..:-+=-=-::**++-++=---:...    :--*+==-:    :::.                                                
                                   ..::::-=++-:**++*####-:-::.   ..:-==::.    .:-                                                 
                                   :::::::-===-:-+==+##*+==:..     -=:.                                                           
                                    :::--:::::----=--+*+--::.                                                                     
                                   .:-----:::.:::::+==-=--::  .                                                                   
                             .....::----:........::-===++--:.:..                                                                  
                             .-:-:-:-........ .....:--=++---:::..                                                                 
                            .:--=---: ...... .  ...-==-*#+--::::..                                                                
                            .::--=+=:....        .:-+*+#%*+==-::..                                                                
                             :.::-:-:::::..      ::=##=*%+++==-.                                                                  
                              .::::::::-...  ..:..-=+==%+--:...                                                                   
                              .:::-::::   ..::----:-+=**::..   .                                                                  
                               .:.:::-. ....-::-:---+%=.:-:.                                                                      
                                ...:---=:..-=-:---::===:...                                                                       
                                 ....:=+=+**++++*#:+--::..                                                                        
                                 ......:::+###+==+*:...                                                                           
                                  ... . . ..........                                                                              
                                   .:.  .    .....                                                                                
                                     .        ..                                                                                  
                                                                                                                                  
                                                                                                                                      
                """;
        FRAME[88] = """
                                                              ...::--::.             .                                                        
                                            .....::::::::-=-:.          .:                                                        
                                           ....::::::--=-===-::...   ...:..                                                       
                                      ...:::-========*+*++**-::::-:::.::....                                                      
                                     ..:------+-....... -++==+::-------::::::.                                                    
                                     .:-====:-=======--=-*-:+:.:====+=----::..                                                    
                                 .. .:--=-==-::......:=.:#=-----::.:::====-::..::::..                                             
                                  ...::---:.:--+:::-=++==#=*==----:--..-=:=:-----::::..                                           
                                  .:=::-:.:-==-....---=*%-=#%=--------=+-:--::--==---:.                                           
                                  .:=:-..-=*=-:---:::-=+--*#=-=-----*=---=-------=======+--                                       
                                 ..:--:.:=#+=-:::--:.---:=:-+-+=+=-=**+-------=-=====-+=++++===                                   
                                  .:-::-+*++---=---:-=*+---=+#+-++-=+=----=++==++=-====:     .-                                   
                                ....=::-+%*=-==--.:+*--=-:-+##%*=**-==-*=+*+**==+=++=-:       --..                                
                                ..:...::-*#***+-:.--*::+=+#++++*%%%%#**+=========-:...      .::- +.:                              
                                   ...-*::+*=---==+====+#%==+**#*++*###*=--::.::-                                                 
                                    ..-#=:----:-+#-::...::**+-+=*+++-.        :-+                                                 
                                  .. :---::=.:=+---::.-=:-+*##**#*##=           :-=                                               
                                  ...::::-=+==:-*+::+. =-=-++*+                 .==                                               
                                  ...:::::--=--:-++==-:==#+=*-.                 --                                                
                                   ..::---::::::---=--==+*+-.                    .                                                
                              . ....:::::-:.:.:::::...*=--::.                                                                     
                             .....::::::..  .....::--*=#=-::.:...                                                                 
                            :::-:::::.....   ......::-+-+--:::....                                                                
                            .:::---::.   .        .-=+-----:::.....                                                               
                            :::::::-.::.  .      .:=+=-:--==---:.                                                                 
                             ..:::::::::.... .....:=*+++=-*==--:.                                                                 
                              .:-::::::.    .:...:--==--#=*::..                                                                   
                              ....::::.    .:-:::::-==++.:.                                                                       
                               ...:::-::.  .::::.:-+**+..:.                                                                       
                                .  .:---:.::--::---===-:..                                                                        
                                 . ..::::-=+=*#=--===-::...                                                                       
                                 ...  ..::-*##*=--::....                                                                          
                                  ..       ...   ....                                                                             
                                   . .       . .                                                                                  
                                    ...       ..                                                                                  
                                                                                                                                  
                                                                                                                                      
                """;
        FRAME[89] = """
                                                             ...::::---:.   .                                                                 
                                            .....:.:::::::---:.=        ..                                                        
                                          :-...:::::::--==-==:=...   .. ....                                                      
                                       ..::-====---===+*#*+*==:::--::........                                                     
                                     ..:::-=*+-=:.=:...---==+::-:-----::::::..                                                    
                                     .-=+=--=#===++-=-==--=:...-======----:..    .                                                
                                 .  .::==:-=-%:+++:-.::---+-=---:...:-===--::::::::..                                             
                              :    .:----+::++==-:---+*+----:::-==-=-....::=----:-:::..                                           
                               -. ..-=:-:=-:%=-++=-+=---:--==+*=+-:-===---------==----:.                                          
                                ::..:.-::=**+==##+:-====-----*-------=----=-------=====:.                                         
                                 --::::::-==:-*%+--+--==-+**-=-=-=-=--------=--====+=**=+=::                                      
                                  :--::-----:+:-=::*++==--===--=---------======+++#-:.::::.-=                                     
                                  .::--=-----.--=++*-=+=-::-==----=-**=+=**+**====+=+=:.   --                                     
                               ....::-=%%*#*----++*##*=#%*===+**++*###++========--:..     .==..                                   
                                  :-=-.*%=+%+-=+-+++###%#**#%###**=====-----=-:.        -:.-.+.-                                  
                                 .--:---+--=-*#=*==+###%%%*==+*-------:.   ::=..           .                                      
                             .-..===:--======+%-+%##%#*+%%*+#*-:-.         .-=                                                    
                       .        :---:::#= ..:-:#++===+==+--::                -*:                                                  
                       ..       ..:--:..::::.:-++**+-*#*++-::               :==:                                                  
                                 :+..::.:-  .====***=:---:-.                -=                                                    
                            ... ...-.:::===:---+-=:=:-::=::.                .:                                                    
                             .:.....:::.--:.-###++---+*%=+::.:.  .                                                                
                           .:::::.:::...:----====*#-=--+*--:::.. .                                                                
                            .::::--::.  ..:=:*#-+=.:==*:++-:::....                                                                
                           ..::::::-:::: .-==:-* .:==*#=+++=-:-:..                                                                
                             .:::::::..:...:.:...::=++=--:::=--:.                                                                 
                             .:::::..::     ....::::=-::::.....                                                                   
                             . ....:::     ::::::::-==-....                                                                       
                              .....:::::.  .::::.:-=++-. ..                                                                       
                                   .::--:::::::::--+==-:..                                                                        
                                    ....:--=-+*=-----:::..                                                                        
                                 ..   ...::=***=-::::...                                                                          
                                  .               ...                                                                             
                                  .            .                                                                                  
                                     ..        .                                                                                  
                                                                                                                                  
                                                                                                                                      
                """;
        FRAME[90] = """
                                    -:                      .+....:::::-:.                                                                    
                         :=.                    =::...::::-:--::.                                                                 
                          -=.              .....-----:--==--=-::..      .                                                         
                           -+:         ....:----:-:-=-==**+==::::::::.........                                                    
                           .=+.--   ..::::--:-=-=:.....--===-::::-----::::::..                                                    
                           .-=*.     :-=#+=---=====-=-==--:..::-===------:::. ......                                              
                         --.-==      :-==--=-:.......:::::-=----:..:--====-::::::::...                                            
 :-                      .+=:--=-  .:--:--:.:....:::-=**=+=::::-=-==-:...-:=----:-::::..                                          
  :-                      -=--+:*+ .--:-:..:=--:..::::-=+===:::-==:-=--=--------=====--.                                          
   .-:                    :==+=:=*::-:-:.:=+=+-::::::.:-----------=---=----------==++==-.                                         
     :--                .--=-+-::===-==-:++++-:-::-::-:-----=-===-=------------====-=--=..                                        
       -=::-=:.:-:   :. ------=-----==-----+-------=-==-====------------=-=++==+++===-==.                                         
         :-+=:---=-: -:..--:-::-- ---------=*==-=====--------------=+++*=***+##-+=-=++-                                           
            .--::+--:.::. ...=-=---=-:-----=+=---------:=---=+++*+**###*-:.---::.-=-.                                             
            -. :::-=-::::.=-=-+#+=*=---==--=-----=---++***+**####*+=====-----::..-*                                               
             :+*---::.:::-:.--%++*+=---==--+++======+####%#*=+==+-=----:... ... :+*                                               
                :-:..:===-##*=%##+%*=---=+=+*--===+=*++==+=:=------.         .+.+.=++=                                            
  .:.            :::.-:  ...-=--=--=*---=-=+++====+===------:.  .+=          -. =::- .:                                           
     .            ----. -=#=:-=-*+%*----=-=-*+++=++----=-::::.  .=*:                                                              
     .               .:--.. :== +=::*%*--#-====#%++*#**-::-:.    -*..                                                             
  ..                  .=:--..::+--+==**+-=*-.+==--=+++----:.     :-*=                                                             
     .::.              :::::.::-::--#-.:--====+-=::==++--:::.:. :=*-.                                                             
             .::..:::::--.:-:.:.:*:.----=--=+==-:.:---+++:::::..=-:                                                               
                      .    - ..=-.* .*#%%%=+:-===..:=+*#*+-::...=+                                                                
                           :::. -:.::..*%%%-:+:===-==***=--:--::..                                                                
                             :. :.--::-::.:::-**%*=--=::.:::--::.                                                                 
                             .:..:.:.::=-.:=#%*#*#-==-::.....:.                                                                   
                               ..:::..-::=-=#=-*%+#*=--....                                                                       
                                  .::..:-=-+:#++-------:. .                                                                       
                                   ..:-:::-*=-:-:=+=---:...                                                                       
                                    ...:.:..--=%----:::...                                                                        
                                 .    ...:::-=*+--:::...                                                                          
                                                   ...                                                                            
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                     
                """;
        FRAME[91] = """
                                        =+-                 .........:::-:.                                                                   
                             +=-               .:::::.::---::--::.                                                                
                           :-=--:            ...:--::-:-==-----:..                                                                
                          :+=.--=    ......:---::-::----+*+---:::::::.. ......                                                    
                         ::+-==---  ..::::------=::..::--++=-::::-----:::.:...                                                    
                         :--=-*=-= ..::+*+--=:====-----=-::...-==-------:::.........                                              
                          :-:-++=---#:-+-=---:.......:::::--=--=:.::--====--:::::::....                                           
                          .:-:-+=++.%#-=--:...::.:::--+*++=---:-==---....-=-------:::::..                                         
                           :.::-+=++#%=::..----:...:::-=+==++=-=---:==-------:---=====-:.                                         
:                  :--:---::::::+==--==#=-=+=:::::::..::----:-------==---=-------==++==-..                                        
-==----:.=+-.    . :-:  .:=-:.-:-+=----=-##+=::::-:.-::-:--==-=-=---------=-----==--=--=..                                        
...:=++-==::-+-----::--:.:.-=:::::=---::--*+-=------=====----------------======+++===-==.                                         
    :..:-=*::.:-+--:.:--::...:::  :::--.=---==-++------------=-----*+=++++**+++===++++-                                           
      --:..:-=+..:-+--::::    .:::. ::.-=-------------=----=:=====+=-+**+====+=====:.                                             
       -+++-:......::=+-....    .--- ::---==---=-=--==+#*+*#*-+-****++++-++---:.                                                  
        :*#====-::=..:-=--:. .:-:=-..-*=##+--*==-====*%%##%#+-:*--:-:--:-+. ...                                                   
          :*%%%-...:-::=+-::-.-::----.:--#=#=+-=--===*++====--=--:-:   -+:                                                        
          .....:--=++=-=-=-:-=+*#%%%%#+#===**%+=--*-===:----::-        -*-                                                        
          .-::::....     --:-::::::::-=*%*+=+=---===------::::      ::=-=+=:.                                                     
            .:----==-::.. :--:....:=..:+--+==----::---=*=-:=:      +:-+ := .=-                                                    
               ---=*=-:..=- . . =%%%=:: .:::::.==+*%%%%#----==-    - .- .=   .                                                    
                      :--=-:-:*-.:::.:..----:-:-: :=##=---=*+++.                                                                  
                 .-:.  .---.-=-:..:  ::.--=-::=*=.=*-::-##+-:-#*.                                                                 
                 :::-:-=-. :-=++=-++=-::::---...:==-:=#*=-==:::-=.                                                                
.--::.           .  .::.  . .=..:::..:--:---=: :---=+--*------:.::.                                                               
          .::::. ..::...:-:. .: .  ...:::-+--:.---:::==--=-=:-:..                                                                 
                  ..:.::::::::::::--:.:=+*#*.  ::..:+%++*%%*---==.                                                                
                  ..   .:..  .  .- ...  .-+=::-    ..-#%%%%%#-:-+-:                                                               
                              : ..=..:    ..:-. ...:..:.=%%%%=::+:-:                                                              
                                   ... ... -:::-::::.-....=#=-:-.=-=+:                                                            
                                 :.......:-=:.:--.-:-::.==*+--:--*#+#+=:..                                                        
                                  .. .. .:-....-... ::--::=..=+##***#+=-:                                                         
                                     .: ..... ....:  :.:--=**+=-++==#%+*-                                                         
                                             ::  ++.:..   ..-.=##*:=-+: :                                                         
                                                  .::...::=.**=:*=*. .+-                                                          
                                                      - .:::-:%*# .-                                                              
                                                       :  .:: ::-  +.                                                                 
                """;
        FRAME[92] = """
                 :..:+*-.                                   .....:::---::::.  ==-:    :::::=-+%+.                                             
      ::.:-**-:                                 ::....:::++-:::::..+=--    :-:::=-**=                                             
       .:.::-**-:                   .       ....::::::-=::++-::::. .*::-. ..:-:.:==**=                                            
         :..:-=##--                 :::...:::--::::::----:.:+-::::-::::-::...:-:.--=#*-                                           
           :..::-+%=-:             .:---:::----:--=-:--:=+-:.---:=:::::=-.:....-:.--=%*-                                          
             ...::-=+#+-:          .=:---==-----.....:::.::::..:::::::::--:.....:.:--+%*-                                         
               :...:-==*#+-:.       ----:-----+%#+-----::::---:..:::.::-::-:.::. . ::-+%+-                                        
       .:-====+##*...::-=+*%#=-:.  .:-==:--::..:+###=-=--+::.::::..::. ...::...-:   .:=*%*-                                       
   :-=-::::-:--++==+:....:--=+#%#+-----:----=*=..:=+*%*::::-+: .::-:::. .. .-.  --    .-#%*=:                                     
       .....   ..-+====:...::--=++*%%#+----:...:. .:-=+#%=:=-....  .:-:.  .. .:     :...:+#*=-:                                   
                    ..--:......:::-==+**#%#+::::.:...:--=*##+:.:..    ..:.     ..    ::..:*#*=---.                                
         -----.     -:::-=:-:......::---==+*#%%*-..:...:--==+#%*:......  .:      ..  . ....:==:::----:                            
           .:=%=--: .-:---+-:-:..:. ...::::--=++*%%#-. ..::--==+*%*-   ...  : .    :. ::   .:-:::...:-==---=----:.                
              :=+-::-:::--:--::--::--:.......:::---=+#%#=...::-==+*#%+-. ...  .       ..     .::--:.....:---::----::=:            
                .-=-:::.:--:=-==:---:---::.........:::--=-. ..::--==+#%%+:. ...   .::::..     ...:--=======-=-:.:::--==.          
                   :-:::---::--.+*+=.**-::--:::...... ...........::--=+*%%*-. .    ..      ..::.....::::-=--==-::==-:---:         
                    :-..:::=---.:=-#::*%%*=-===:.:::::..     .......:--=+#%%+-:::-----:...:-====.::.::-::.:-==----+#*%*=-.        
                     .=:.--. --===:=:::=%%%%%%#+++++=------:..   ......:-=*%%#=--::::....::-==- :=:-++=:..:...::==#%%*#%%=        
                       :::.:::-------:. .+%%%%*=-+*##**++++++==-:. ......:-=*%*=----::...     .  ..:::::----::-=+*#=*%+*%+        
                       ---=---:.::...   . .*%%%%%%#*=:......... ...-........:-*#=:::::::-----:::::--====-:-----==+*%=-++=.        
                     ::..:::==-...-==:. .... :%%%#++=+*%%%%%%%%#%%%=:  . ......:---:::::::..::::::..::::===*#####+*#=:==          
               :::--::........--:.. :::.:         .:--====++++***+==-::.:.   ....::----==++++=++==--=+**#%%%%%%%%%%=:-=-:         
           :--=-:::...:::---:..:-:...::: ::.     ..:::::::::.:.  .   .-: ...::-=--:::------==----------==++**%%%%#*+*#----:.      
         :---:.          .:-====-::::::.:.:::---..  .:::::::....            .--+#*++++++++++=---:::::::--======-::===%=--:-------:
                           .--===--::..::.:..::--==--::::::::::.:::..        ....:-+**+*******=-::....::::::.....:==#%*-:::=#*==-:
                            .::==+#+---===+=---:::..   .::-=***#======--:..     ....:=******=-::::::::...........:=%%+--*.:=----::
                             ..+::--+=+=::::-==========--:::........::-=+**+=-.      .....:::..........:=*+:  ..:--+*+=:.**---::::
                               :+=:.::::-+--::.:::..::------====---:::......:-=-:   ..       ....  ..-*#%%%=.::::::=-....:-::::-=-
                                -+-=-....:.-::-::.::::::.:...::::---------:::..     .--:. . ...:-----=+##*--::----=+.....:::::-*--
                                :-+--::........::::::........--.:.    .:::------::. .:==::.:=+.......::-=-.:.:-----*=:::-+=--*#-:.
                                .-=-=-..-------=--::.........    .:::.........::::  ---::::::-*+:    .....:...:---=**-:-------=:::
                               .::--::.=-::-====+=-::::::::::........:--:..  --:    ----------:::::.   :-::::::-------:::....:::--
=:                             -=--   ..       .=-::-=+==-------==+++-.    .-=-     :-----=+#%#+==--=+#*--:::---==----:...........
    :-=-.                      :=:+-  .             ---=====+++++-..    ..:--  :.  ..+:::::-+%%%%%%%###%####*+++==--:::-=-:...   .
         ..:-:.                .+:                :--::.:::.....:-=--:::::. .-:   :.  :....::-==++++=---:::::::::---:::::::..:::::
               .::::.            ..            :-::---::--------=++=-:..::-:.   .:..   .    ..::----::::::........::::::---:::::::
                     :-::.                   .-::--.:::..   ...::----:.               ..       ..:::::.....     ............:::::-    
                """;
        FRAME[93] = """
                                                            .........::...:::=---:::::::::::::---=---=#####%%%##==*#+--.              .--==:  
                                                ......:--:-::::::...:-=+****************###*+-=*%#+++#*=--+*+-:.           *=+=-::
                                           .....::.:::-=:-*++::::...   .........::::........::=*##++##=--:::==*+-::.    .::-=+++*=
                                      ....:::::::.:::-::--::*#*=-::::..  ...=+...-::-:::::........:+%*+##-::::-++#*=--:.::::-+-=+:
                                    ..:--:----::-:=-::-=++-:.:*%#=---=::..  .:+- .::-:-=-::-:........-+#*+#+::::::*+%#==**=:::-:=+
                                     .:--==---=+*=----=-::..--:.:+#%#::=::::::::-:-::..... ..     .....:-+#=*#-::::.===*#+=**- -::
                                    .:-==-----=+*=+-==--:-==----::-..::.-=--:::....::---=====++++++=....:::=**=#+::::.:---+*=:-.::
                                    .:-==-:::::-*#+=--=++==-----==-:==*--:.  .:::::::=*+======+*#####*: .::::-*#-*#:::...:::-=+=::
                                   ..::-+#+-::::+++=:----====+++=-=-=-:. .-=--:::::::::::::::::::...::::. ...::-+#=+#-:.....:::-==
                                  .:-----===-::.:+--=::----=---:--=-..:=++=-:::::.......::::..............  ....::+#=+*=......::::
                                 .:-==-+#%#*+===--+:-=::---:==---=::--:::.....::-=+=::... .:-:...       ..:......:::=#=-++.......-
                         . .  : .---=--=-:--=#%*=-=+:--:---=::--------:.-:::.....:--++::...  .-=.     ..... .:.....::-+#=:=*:::::.
                       :-.::-::::--:-=-----:-+*%**+=-=+----:--:::-=++==*-.:...::::.:--#+::...    +-            .:....:::-+=:-+::::
                        --------:---:----:---::-=**--::::-==-:--:::*#**+++-::..:--=-:::...... ..             ::.     ...::---::-:.
                         --:-:-:::---::---.-:--..:-=+-::::----::..:--:---#*..--..-=-++-:..:::.                :-::.     ...::---..
                          :.::..:....-::.:-.:.:-...::--::::::::=+:.. .::-:... .-=..:=-=+++-::..:-:::.          .:--.....    ...:::
                        .---:..... .....:..: :..: ...:--=--:::::-.::..:.  =-:.   .*-:..-======+-::...  ......:..  :--....      ...
                       .-----=-==-::...  ..  ... .....:--:---=-:--:.:..:::::: :::.  .-=-:..:======---::::::.. .     .:-:...:. ..  
                       ---:::-==-==------=--=-:... ...   ..::::-:-+:-:.. ...........    ..-=--.. .::-::::::......      .:-:. .... 
                       --:::::::::-----:----=----====--=--.  ...:-:-+...... ...:::......      .::==:...                   .:-:.   
                      :=--=--:::::-:.--::---:--=-:-----==-::.::-:..:-==....:- .....:::::::::......:::--:.......               .:-:
                       .:-===-=-----::--::=-::-==::---------:::::.....:--:.:. .::.         ..........:..............              
                       ..:--====----------------=-:-===--==--=-=--:.....:--:..:..  ....:::-::....::::::.::....::.........:::..    
                      .-:--=====-------------------====---=---::==-::-==--.:-++:...:-:                .....::..--:..-::.....      
                     .----:--====----:--------:-------=--:----:::+*+-::......:::-+*=:.....:-==-:..             ::=+-: ....:::... .
                     ---:-:-::-+==------::-----------------:::.. .....          ...:--=+*+=-:::.............:.--:.-=*=:   ......  
                    .--::.::-::.-**==*+-------:::----:::--:::::.::::::::............. ......:::::::--------------:..:**=:....:-:. 
                     :....:-::. .:::++=+=#*-----:---=::::::::::::::::::::.:.............          .....::::::::::-....-#+-:.  .:..
                     :....:-:      ..:::-==-:.:----#==-----::::::::::::----------::::::::...................    . :....:*#=-:.    
                     :...:-.        .  ..:.:--:+*=-----::.....::..................                               . .... .-#*-::.  
                     -:.-=-            .:-::+#+=-.  ............:..:::::.......... .:==-----::-:::::::...  :::::..  ..... .-#*-::.
                     :.+*#=:          ::=+=--::::.:--::.:...     .:::.......  ..::-:......:::::::.:====--::::------:. .....  -#*-:
                     .::--*:.                        ..-::..   .             -::..............   .=-==--------:::::::::......  .%*
                       :---*-::                            ....           .       .::.::.  .:.::::.........:::::-:...:---:.....   
                         ::--=:.:                                .       .               .::...................     =+-----: .... 
                            ::=+-:::*#=                              .               .           ...::..    ..:..:::.. ....:::....
                             .-::::-:.:.                                      .....  ..                   .::....................     
                """;
        FRAME[94] = """
                                                            ...........:....:.                                                                
                                                ......:::..:::::.::.                                                              
                                          ..........:::--:--::::::.        .                                                      
                                   .  .::::.:::::::::::::---:::::::::..   . .                                                     
                                    ...:---:-----:::::::-=+=-::.:::::.::..   ...                                                  
                                    ..-----::-=--::---:---:----:---:::::::.:::::...                                               
                                    .::---:::--:-::---==--::--+::--=---------::::.......                                          
                                    .::---::::::....::-==--:..:-----:..=-==----:::::::-:..                                        
                                   .:::-+*=:::......:::--=--===++=-------:::=-------=---...                ....                   
                                  .::--====---::....:::-----:------------+-:---========--=...               .......               
                                .::-----==+=---::-.:--:-:-:---=+::=-------=+:.:======---.:+:....             :........            
                      . . .  . .-:-:--==-:----------==--:-------+-.:-----===#=:..-=--==:...*-:.....          ::........:.         
                       :-::-:--:-:--:.--==.----=:-:----:-::------=-:..-==*#*=#=:...-=*.    =+::.....-         -:. .......::       
                        :---------:---:----:-:---.:::-----::::==+=+=:..:--+==+#=:....::    .*=:::.....-:      -:.. .: .....:-:    
                         ::-.:.-::+:::-.:::-.:----.:-----:-:-::==+-++::..:---.*#-::....:-.  =*-..:......--.   =::.  .:.......:--. 
                         ::....:.:.:.:.::...=....:. ::.:-..::-+#*#=-==:-#*+-===+**::--:.:-=:-**=-::...  .:-=: =::.....:.      .:-=
                       :-----:... .. ..  ... ......: ::.:-:.:::=**=----..#*+=+==+**::---:.-+-**+=-::::.....-===-:::....:.        -
                       ----=-=-===---=::-.. .  ...  . ::   -..::.::.-....:-:::.=..:::-.::...--:-:::.:........==.::......-.        
                     .---::::--=-=---=--==---===-:=-::  :.  .  .     :.  ..     :.   .:       -. ....:.       .-...... ..:        
                     .--:::::::::::-:-----==------==----==---:.:--:..    ..        .  ::       :     ..         :.      .:.       
                     :-------:::::--.:-:.:---::---:-===-=--:::.:--::.....:::....... .:...        .    .:          .      ...      
                      :---====-------::--:-=-::-=-:-----+---:-:.::.:::....:::.................... .:....          ..      ...     
                      ..:-=-===-------------=---==-:-===--==---=--==--:....::.........:.........:. ...............  :........     
                       .:--==-=----------:-------==--===--===---:-==--::::-:---:--:.....:-......................:-: ..............
                        .:-====---------------::-------=--::=----::+*+--::.:-----:::::::---:-=-........=:-..........  ............
                          .:--=+=---------------------=+====+++==-::::--:::::-+**=:::...::--:::::::::-::#---:..::........-::..:...
                            .:-==##+---------::----------=++=====++=-=+++=:::::::::::::::::=++-:::.....:-:::::::::-:::::.. :--::..
                               ::=+=+**+---------:::::--=++**=:===+**+========-:--=---...::::-:...::::::.-:---::::::::::::::#-::::
                                  :::--=#++=---::::---::......-*+*+++++-::=*****======+==--:.:-:::..:::::::::----:..........-:::::
                                     ..::-=-+*==-::-::::....::-......:-+==+**++++=-------===-----=-----:..:..:.::....::::::::.::::
                                        ..:::-=--:.:.. ...-:::::.......::..:-==++++=::=*####+=--------------::.:-::::::::::::::..:
                                          ..:.:-::---...... ....:::.::::::........ ..-=#*+===----:-:::::------::-------:::::::::::
                                               ::=--::::.          .-:.......   ..:.:-......-++==--:..==++===---:-----------::::::
                                             .:::-:-=::+=+:     .:          .::::.....:............ .+====--------::::::::::::...:
                                             -::=------.:--==.  ..             ::..........   ...:-::.....:::-------::....:-------
                                            ---==--:::--:-::=#- .:::...... .           .......:.....:...............:::--==-------
                                           ..-:--:::::::=+:+#+::     .......                .::............ ........ .  :=*-------   
                """;
        FRAME[95] = """
                                                            ...   ............                                                                
                                               .......::...::.......                                                              
                                        .............::-:::-:::::.:.       .                                                      
                                  .  ..::::::::::::::::::---::::::.....                                                           
                                    ....:-:::--:-::::.::--==-::.::::..::..    ....                                                
                                    .::::--::----::-::----==--:--:::::::...:::::...  ..                                           
                                    .:::--::::::::.:----===--::--::=-------:::::::.......                                         
                                    .::---:::.......:::-=---------:---+-====---:::::::-::..         ..                          ..
                                  ..:::-==-:::.......::--=---====-----:-::.---------.:--::.          -....                   . . .
                                ..::::----=-:-::.::.:::-::--:--------:+::-------====*-..:.            -:.....                 .. .
                                .::--:----==--:::.--:-:--:-:=::--=-----*=:.:=-======-*+:....           =:.......              :.. 
                      .. .  : .-:.:----:---:-:---:--:-:-===--=-::----=--++:..:====---=*+:....:.        :=:.:.....::           -.. 
                       :--::::-.:-.::----:---=:------:-=------=-:..-+===+++-...:-+++-::#=::....::       +:...:.....:-.        ::. 
                        :---:-:--.---:----:-:--::-:---.::==--+=+=:..:-=*==#*::...:-:.  +#=::.....:-.    +-:...:......:-:      ::. 
                         :::::::.-:..-...-:...--..::---.:--=***-==:==----:=**-:-:..:=: :#*=-:......:-:  =-:....:.   ...:--    -...
                         ::.:.: :...:.:.: ::.:..: .:.:- .:-:=+++:-=:=##*=+:-+*-:+*=--===****=.:::...:==:===-::..-...    .-==. -...
                       :-----=-:.... .  ..  : :  . .:. .: .-...::..:::+:..=-.:-:::-:..:*--+++-:-......-*.==-:::..-..      .--:....
                       ----=-=-====-===----:.:::     .   . .:.   :  .:.    .:  ..:      -:.....::       --........:         .-:...
                     .--::::::-=-===----==---====--=-=-:.:-::.   ..  .:.         ..       -    .:.       .-       .:          ::  
                     .--:::::::::::-::-----==------==---:===::...:--::.:.. ..:.   :.   ..       ..         :      ..           .- 
                     :=-----::::-::-::--::-=-::------==--:-:--::..:-:::....:::...................:.   .::.            .           
                      :------=----:--::-----=::-===:-==---+=--::...:.::......:::......  :..............:......... ....  ..::.     
                      .:--=-==--------------=----==-:-===-====-::-=-===:-:....::..........:.......   . .................  ........
                       -----=--------------------===:====-:-==----:-==--::::::----:--::....:-:...........:..............  ........
                         -:-====-:-------------:------===-:::-=---::-=+*=-:::::---::::::::::-----=-.........=::...................
                          :-:==-=----:----=-----------=++*++-=====:-:::.---:::::-+++=:::....::::::::::::::-:-#-:-:.............-::
                           .::-*++==----:::----:=---==---=-=++++++++=--==+---:::::-::::::::::--==--:::::....:-:::::::::::::--:::..
                             ..:=***-*+=------:-::::::--==++**+---++++++++++*+==--:--=::::::::::-==::..:::::::-:::::::::::::::::::
                                 .::+#+=-:*--:---:-:.:::.:::-+**=+++***#+---=-+**+-=+++++=--::::.:::::::::::::::-:---:-::::.......
                                   ..:::=#+-:::--::::::::.........-+*++**+::+****##+=--------=-::::----::::::::::..--::....::.::::
                                       ..::--+*-::::::...:...:::::.........-+#*+=====--:::-=+**+=------=----::::::::::::::::::::::
                                          ...::---:=-:....  -:..........::-:....::-=++=-:-****++==--------------::.::::-::::::::::
                                              ..:::.::.......... ..:::::::::............:--*+=-------:::::::-=-----:::----------::
                                                   .-=-:.....        .::........   ....:-........-=-----:.--====------:---------::
                                                         :.        .         .....:....................  :*-=-----------::::::::::
                                                            .     ..             :::..........   ....::::......::---------:......-
                                                                 .            .            .....  .......................:----:.=- 
                """;
        FRAME[96] = """
                                                            .     ...   ......                                                                
                                            .    .. ..:.....:........                                                             
                                       ...............::-::::.......                                                              
                                      ......:::.::::::..:::-:::.:::.. .                                                           
                                     ..:::::::::::::...:-::--:::::......      ....                                                
                                   ....::-::::--:::::::-------:-::::::.:....::::....  .                                           
                                   ..:::::::....:...:-::------------------:::::::.......                         . .              
                                  ....::::::........::::--------==--------==---:::::::--:.. .:..                  ......          
                                 .....:---:::::.....::.:-------==----::-=====-::-:------:::. :-....                .......:       
                                ...::::-----::::.....:-::::::----=:-----------*:..-===---:.   -=:.....             -:...  ...:    
                               .:::::::-:---::::-:....::-::-:-----+=::-------=-*+:..:-==-..    +=::....:.           -.. :......:: 
                           . ....:::::---::--:-:.:-:-::=:--::------=+:..---=====#+:...:-..     .*-.::....::         -:.  .:......:
                       ::::.-:.-: :-:-------:-----:-:-----=-:.:---=-=+:...:=++===#=::...:-.     ++:..:.....:-.      ::..  .-......
                        :----:-::--:----:----:-:---.:::---:=-::.:-+++++:....--=-:*#=::....:=-   -*-:.:-.  ...:=:    -:..   .-.    
                         --:-::.::.+...--...--.:::-- .::-==:::-##*===--=:-#*+-==:-+**-:--:::-=- :**+=-.-....  .==-  =::......-    
                          :.::.-.::......-.: :- :..:. .-.:==.::-+#*=*:.:=:-++-:-#-:-*#:::::...+*.+*+=-:.-....   .**:=::.......-   
                        .----:-...... ... ..:  : ..  . .:  .-..::.   ::....-     -...::.:.     .+::::.. .:        .+....     .::  
                       .----=-======---==----:..:...  .  .       .    ..   ..      :    .:       :.      .:         -.        .:. 
                      .--:::::--=--==--=---=---===---=---=-::--:.:.  .:..   :.  .. .     ..       .      ..          .         .. 
                      .--::::::::::::-:-----===------===--:.-==::....:--:.......:....... .:.  :.....      .. .                    
                      ---=--:::::-::-:::-=::---::----:-===-::-:--::...:::::.... ............  ............... ::.......           
                       ------===--:::--::-::-==::-===-======:-=--:::...:..:.......:.........  :.............  ..................:.
                       .::--==--------------::-----===--===--====-::-==--=-::::....::...........::.:............:.................
                        :----==---------------------==--===--:-===---::-==-::::::::--:-::--:......:-..............:.:.............
                         :---==-=-----------:--------=-=====--:---=--::::=+==-::::.::-::::::::::::=-:::--:..........=::::.........
                           :--==-------:---------::----===++*+=--=----::::=-:-:::::::-==--:::.....-::::::::::::::::::=*-::::......
                             --=**---=-----::-----=---=+======+=+++***+--=---:::::::::-+*-:::::::::::---:::::::::..:.=::::::::::::
                              .-++=*#+----=----::::--===++-=-==**+======**==++**++-:-::::::::::::::::--=+--:::::.....:-:::::::::::
                                 :-*#+**#+---::----:::...:-++++++*+:.=+++*#+==========::-----+:.:..:::..-:::::::::::::::::::::::::
                                  ::::-++-*+*+-:--::::..::-....:.:=+-++++++++-----=+#**+-====++==---::::-:::::::::::::::::::-----:
                                  ---- .::-*=-:-::-::::::::::.....:-...-==+***=::*******+=------------::::------:::::::::::..--:::
                                 .--+:     .---:+-:::........:..:::::...........-+*+==--===:-::::==+**+--------------:::::::::::::
                                  -*          ..----:-:.....   .:............:::.....:-***+==-.-+**+++==----------------:::...::::
                                    .             .::.......... .......... :::.::............ :==+==--------:-::::::::------:::::-
                                                      --:.   :..  .       ::......... .. ...:-:....:--==------:....-=----------::-
                                                           ...          .     ........  ....:::..............::--:=--------------:
                                                                       .               .:...........   ......... :+=---------:::--    
                """;
        FRAME[97] = """
                                                            .     ...   ......                                                                
                                            .    .. ..:.....:........                                                             
                                       ...............::-::::.......                                                              
                                      ......:::.::::::..:::-:::.:::.. .                                                           
                                     ..:::::::::::::...:-::--:::::......      ....                                                
                                   ....::-::::--:::::::-------:-::::::.:....::::....  .                                           
                                   ..:::::::....:...:-::------------------:::::::.......                         . .              
                                  ....::::::........::::--------==--------==---:::::::--:.. .:..                  ......          
                                 .....:---:::::.....::.:-------==----::-=====-::-:------:::. :-....                .......:       
                                ...::::-----::::.....:-::::::----=:-----------*:..-===---:.   -=:.....             -:...  ...:    
                               .:::::::-:---::::-:....::-::-:-----+=::-------=-*+:..:-==-..    +=::....:.           -.. :......:: 
                           . ....:::::---::--:-:.:-:-::=:--::------=+:..---=====#+:...:-..     .*-.::....::         -:.  .:......:
                       ::::.-:.-: :-:-------:-----:-:-----=-:.:---=-=+:...:=++===#=::...:-.     ++:..:.....:-.      ::..  .-......
                        :----:-::--:----:----:-:---.:::---:=-::.:-+++++:....--=-:*#=::....:=-   -*-:.:-.  ...:=:    -:..   .-.    
                         --:-::.::.+...--...--.:::-- .::-==:::-##*===--=:-#*+-==:-+**-:--:::-=- :**+=-.-....  .==-  =::......-    
                          :.::.-.::......-.: :- :..:. .-.:==.::-+#*=*:.:=:-++-:-#-:-*#:::::...+*.+*+=-:.-....   .**:=::.......-   
                        .----:-...... ... ..:  : ..  . .:  .-..::.   ::....-     -...::.:.     .+::::.. .:        .+....     .::  
                       .----=-======---==----:..:...  .  .       .    ..   ..      :    .:       :.      .:         -.        .:. 
                      .--:::::--=--==--=---=---===---=---=-::--:.:.  .:..   :.  .. .     ..       .      ..          .         .. 
                      .--::::::::::::-:-----===------===--:.-==::....:--:.......:....... .:.  :.....      .. .                    
                      ---=--:::::-::-:::-=::---::----:-===-::-:--::...:::::.... ............  ............... ::.......           
                       ------===--:::--::-::-==::-===-======:-=--:::...:..:.......:.........  :.............  ..................:.
                       .::--==--------------::-----===--===--====-::-==--=-::::....::...........::.:............:.................
                        :----==---------------------==--===--:-===---::-==-::::::::--:-::--:......:-..............:.:.............
                         :---==-=-----------:--------=-=====--:---=--::::=+==-::::.::-::::::::::::=-:::--:..........=::::.........
                           :--==-------:---------::----===++*+=--=----::::=-:-:::::::-==--:::.....-::::::::::::::::::=*-::::......
                             --=**---=-----::-----=---=+======+=+++***+--=---:::::::::-+*-:::::::::::---:::::::::..:.=::::::::::::
                              .-++=*#+----=----::::--===++-=-==**+======**==++**++-:-::::::::::::::::--=+--:::::.....:-:::::::::::
                                 :-*#+**#+---::----:::...:-++++++*+:.=+++*#+==========::-----+:.:..:::..-:::::::::::::::::::::::::
                                  ::::-++-*+*+-:--::::..::-....:.:=+-++++++++-----=+#**+-====++==---::::-:::::::::::::::::::-----:
                                  ---- .::-*=-:-::-::::::::::.....:-...-==+***=::*******+=------------::::------:::::::::::..--:::
                                 .--+:     .---:+-:::........:..:::::...........-+*+==--===:-::::==+**+--------------:::::::::::::
                                  -*          ..----:-:.....   .:............:::.....:-***+==-.-+**+++==----------------:::...::::
                                    .             .::.......... .......... :::.::............ :==+==--------:-::::::::------:::::-
                                                      --:.   :..  .       ::......... .. ...:-:....:--==------:....-=----------::-
                                                           ...          .     ........  ....:::..............::--:=--------------:
                                                                       .               .:...........   ......... :+=---------:::--    
                """;
        FRAME[98] = """
                                                                         ...                                                                  
                                           ...............:.....                                                                  
                                       ...................::::......                                                              
                                      ....................:::::......            ..                                               
                                     ........::..::.......:::::::.....          .....                                             
                                   ......:::.::::::..:.:::::::-::..:::......:::........                                           
                                   .....................::::::----::::::-:::::::.........                                         
                                  .....::::.............:::::------:::::------:-:::::::::::..                                     
                                 ......:::::...........:--:::-----:::::::------:-:-----::::.                          .           
                                ......::::::::.......::::::::::--:::::---------:-------:-:.     ..                    . ...       
                              ........::::::............::::::::----:-=---------=::-=----:.      :....                .. ......   
                                .......::::......::.::.-.--:-:---=-=+-:=#%%#===--+=..:-:.        .=:.....              :. . ....:.
                              .. ..:.:::-:.---:-::---=-==--=-:---=====+:.=----=+-:-*:....          =::....:.           ...  :.....
                        ......: ::.-----:-----:-----:---=-=---.:-======#-..:=+=+...+*:...::.       =-..:....:-         .:.   :....
                         .-------:---:----:----:-:---:-::-==+==:.:-=-:.-#-...:--:...#+::....--     :=-..:.....:-:      .:..  .::..
                          .-:-:-:::.:=:::--..:--.:--=+.:-++==--=-*+=*-..=+=:=-::=:  -**+-:...:--    +--:.::     ---    .:......:: 
                          .:...:...-..-.:.=-...+-.:.:-: .:.-*+::-:*#*+*=::--:++--++-:=++=:.::..-*=--=--::.:...   .=*---=........::
                         ------=--::.... . .... .  .  :  ::.:-=  .:::::=:   .-.....*: .....:...  -*-.......::       +*=:....    .:
                        :--:---=-==-=-==------:--:: ::..  .        .    ..   .:     :.     :.      -.       :.       .=.         .
                        :-::::::::::---===-----==---===----=---::. :--:.     :..        :  .:               ..         :          
                        -----::::::::-:::::::----===------:==--:::.-=-::.....:::.......  :.....        .::. .:.         .         
                        .:------=--:::-::-:::--::---=:-=----:----:.::::::::..::::.....:-::............:.:...........      .::.... 
                         .:----==-----------::-----=-::-===-===--==--==--::....:-..........:........::--::..............::.:......
                          :----=----=---------------=--====----==-=--===--::-----=--=:......:--.....................::-----::.....
                           ..--===----------------------===---:====-::====--::::----::::::::--::---:.::......+-=:.............:...
                             .:-=+==-----=--=------------=+++=======:::::----::::-=+==-::::..-:::::::::::::-::*---::::.::....... -
                               ::-==#*-=--=------------=-----=+==++**+=--=---::::::==::::::::::-==--:-:::.....-:::::::::::::::::::
                                   -==++#*=----=--::::::-=-=+++*=---+==+++++***==--::=:::::::::::=**-:::::....::::::::::::::::::::
                                      :-=+=:---:-----:.:-:::::=++*+==++##*=--==-+**==++*++=-:=-.::::.::::::::::::-:---::::::......
                                        .::------::-::--:::.....:..:=++++**=:-++***#*==========--:::--=-::::::::::::---:::::::....
                                        .:-=----::  .:.:::::..:::::...:.....:-***+===+=-::---+***+--========-:-:::::::::::::::::::
                                        --=+=-:---:..=-:.... .-::.........:::....:-=***+-::**###*++=--------------::.:::--::::::::
                                       ---:::::::-+=-.......:..... ..::.::::::...........:-+*+===-----:-:::::--=--=-:::--------:::
                                      .-==-:::::..::=-..........       .:........    .:..:......-+++==--:...==++++=----:----------
                                      :-:.....::::::-::.   .:.....  ..        ......:..:::................:==+===-------::-:::::::
                                      :-::........:-::.         ......            .::........... ......:--:..:-------------..:...:
                                      -:..........::.              .   .        .         .......  :....:::...........:-------...:    
                """;
        FRAME[99] = """
                                                                      .  ..                                                                   
                                            ...............::...                                                                  
                                       ..  ......................                                                                 
                                       .............................             ...                                              
                                      .......................::::.....         ......                                             
                                   .....................:::::::::..:::.::...::.........                                           
                                   ......................:::::::--:::::::::::::...........                                        
                                  ......................::::::::--:::::::----::-:::::::::....                                     
                                 ......::::............:-::::::-:-:::::::----:-::::::--:::.                                       
                                ........:::::........::::::::::---:::::::------:-------:-:.                                       
                               ........::::...............::-::::--::::::----------------:.                ...                    
                               ...........................-=-:::::-----::-+:----::----:-:-..               ......                 
                                 ........:.:..-::-::-::-:::=-====:=------=-==.:::.:::.....+:...             .:......              
                         ... : -..-..--:-::-::==:---+=-=-::+*%%%%+-::-=--..:-#-........    +-. ...           -:.:....:.           
                         .---:-:-:---=-:----:-----:----=--::-*++=-+=..:=:....-*-...:.      :+::....::        .:. .:....:-.        
                          .:-:-:-:-=:--=----=:-----.-:-==---::-::--++:..:-....+*-:...:-     +=:.:....:-       -:. .-......--      
                          .:..::-.:.+::.=::.-=.:.:-: :.--=..--++#=---=:**===:--=*=---::-=   -++-::... .-=.    -:....-      :-=.   
                         :------:..... . . ...- ...-. :..-= .:---=#-.:-:--::+*-:=++-.:..:*+==++=-:-.....:*+--:-::....:.      :==--
                        .-----=-=-=-------:--:..:.  .  .   .  ..   -    :.   :-  ...:.    :*:.... .:.     :*+:....   .:        :==
                        :--::::::-=-==--==--=---==----=---:.:--:.   ..   :    .     ..      :      ..       -.        .:         -
                        -=-----:::::::-:::--:--=------=---::===::...:---::::. .:::.. ..   .::.      ..  .              ..        .
                        .::-=--==---:::-::-:::--::---=---==-:-:--::..:--:::...::::....................    ::....       ..  .      
                         ::-----==--------:--::--::-==::====-====---:.-::::.....:::.........:..........   :...............   .::..
                          .:==--=--------------------==--===----==---::==----=+-.---:.........:::...........:............     :...
                            .---===----------:----------=====----===-::-===--:::.:---:::-=++-.:=--:...........=..::.........::....
                              ::-+*+==--------------------=+++=--==---:::=-----::::-==----::...-:::::::-=-:::::.--:............. :
                                 .--+*#=---=-------=----==-----=+=+++*+==---=-::::::-**-::::::.:-----::::::::::.--:::::::::::::::.
                                    ::-+***=-----=--:::::-=--==+*+----+++=+++***===:--::::::::::::-=**-::........::::::::::::::::-
                                       .:::=#+==---::--:::-:::::=**++==+**#+=--=-==+*--=++==-=-.::::::::::::::::::-::--:::::::::::
                                           .:---+*-=-::--::::....:::.:=+++***-::++*****==========---:::-:::::::::::::-----::::....
                                               ..=---:-:...::::.:::::.........:-***++=++=------=++*=----====----:::::::.::::::::::
                                                  .::::-:......::........ .:::-....:--+**+=::+**##**==---------------::::-::::::::
                                                      :=--...:.......  ..::.::::............=-**==------:--::::::-----::::-------:
                                                            :.          ............  ..:..::.....:=++==---...:==++===---:--------
                                                              .       .           ....:..::.................:-==+==--------:::::::
                                                                    .              ..............   .....::::...:------------:.::.
                                                                                 .            ...... .:....:::...........::---:::.    
                """;
        FRAME[100] = """
                                                                        ...                                                                   
                                              ..    ...  .......                                                                  
                                               . ...... ........                                                                  
                                           . .......................                                                              
                                       ......................:::.......      ...                                                  
                                     .......................::::::...::::..::..... ....                                           
                                     .......................::::::::::::::::::.............                                       
                                     .....................:::::::::::::::::::::::::::::::....                                     
                                     ..................:::::::::::::::::::::::::::::::::.:.                                       
                                 ......................:::::::::::-:::::::::::::::::---:::..                                      
                               ................. ...........:::::::::::::::---::::--:::::::.                                      
                                  ............................::::::::::::::-::::::::::::.                                        
                                   .............................:::::::::::-::...........:..                ...                   
                               . .:..:----:-....--.:-:.::........+:::::.....=-........ .   :...              ... ..               
                          :::.:.:-.:-==---=-:--==::--:-----:......==:::......-*:.:.        .=:....            :.......            
                           ---::-:-----=-:--+:-:-==:-:-+*.--.:.  ..=+..::..   -*:..::       :=:. ..:.         ::..:....:.         
                           .:::=:::.+::.=-..--:::-==.:=--:.-:-:=-   ++:..:-    =*-...:-.     =-::....:-       .:...-.....:-       
                           .:::....... : :....+ ...-= :.:-=:.:-=+#+=----+=-==-::=+-:::.:=:   -=-:::.. .:=.     -....:.   ..:=:    
                          .-------===--=-:.::. . .  .  .. :=  .: .:*=.::::..:*+-:=-.-....=*==-+=-:.:.....++::---:......     .-=-:.
                         .:--::::---=---=--==---===-----:  ..  ..   .    .    :+.   .:    .++..    .:     .**--:.... .:.      .==-
                          -:::::::-:.--:::-----=-----===--:-==-:::..:-:. .:  ::-:           .       .       :-         ..        +
                          .:---=----::--:-=:::--::---=:---=-:-::::..---:..... :::.....:. .:..-:       ..      .        ..         
                           :::-==-----------=--=-:-===:-====-==--::...:..:.....:::...... .:............ .....::..        .        
                            .::----------------------==-====--====-----==--=-....::.........:..........  ................. :......
                             .--==----------------------====-::+-==--::-==---::::-=-::+=:.:...::.......... :......:.... .. .:.....
                                :=*+=--=-----------------==+++=:-----::::+---:::::-==----::::.---::==::-..:..:--::............:...
                                 .:-**+=-=--=----::-----==--=-=++=++*++--:-:-:::::::=#=::::...::----::::::::-:-+:--:::......... ::
                                    -::=#+--:--------:::..-=-++***-::=++++++++**=--::::::::::::::-=*+-:::......-:::::::::::::--::.
                                       .::--#+-::.=-:::::--::......:==+***#+-----=**=-===+=--=-::::.:::::::::::.:-:--:::::::::::::
                                            .--==*=::::--::.:....:::....:-++++:++***##+=-----=-----:::=-::::::::::.:----::::......
                                                .-----=-:.... .:::::::.......:::=++++=++=-:.:-+*#*+----=====--:::::::..:::::::::::
                                                     :-:-:...... .....:::.::::::........:::--#***++==-------------::::::::::::::::
                                                         ...:...        .:::.......   ..:--...:=+===-----....:==-==--:::--------::
                                                            ...       .          ........:::............:--:-=*++===----:---:-----
                                                                   ..             . .::.........   ........:---==---------::::::::
                                                                       .         .           ...... .:..::::.........::------:....
                                                                            ..                     .:..................... . ...:+    
                """;
        FRAME[101] = """
                                                                        ...                                                                   
                                                            .....                                                                 
                                                           ......                                                                 
                                                           ........                                                               
                                          ............. ..................  ....                                                  
                                       .........................::....:..........    ...                                          
                                      ......... .............::::::::..:::::::...............                                     
                                     ......................::::::::::.:::::::::::::::::.....                                      
                                     .........  .........:::::::::::::::::::::::::::::::...                                       
                                    ............. ..........::::::::::::::::::::::::::::::.                                       
                               .     ...........  ...........::::::::::::::::::::::::::::::.                                      
                                     ..........................::::::::::::::::::::::::::..                                       
                                  ...............................::::::::::::::............ .                   .                 
                                  ...............::.....:-........+-:::.......=:......       -..                .. ..             
                          .-=-:....::..::....::...:-:.....=::......-+::......  -=...          -:....             .......          
                          ::----::--.---::-:..:::...:::.   =:.:.   .:*:.::..    ++:..:.        =:....:           .. .....:.       
                          .:------::-::--=:---:.---..:---. .=-:.--   =#-..:-     *+:...:-      :-:.....-:         :. .:....:-     
                          .-----:-.::-=..-=.::=-.:.--..::-==..:-+*#+-:--:=+--=.  -=+:::..-=..  :=:..:.  .-=       :.. .:.....:-:  
                          .:---::.:....- :.- . :: :.:+. :.:== .::-=-*+. .::--:+*=-.:-.:::.:++:----::.-....:==-:::.:.....-.     :=-
                         ...-:--------=-==-=---=--.:-:.. :. ..  ..   -    ..   .+-    .:    :#= ......:     :#+---..... .:.      -
                          .-:--::-::::------=----=---=--:-==---:.:--:::   ::     .  .  :.     :.      .:      -*-.       .:       
                           ..::---:--:::::-:::-:::------------::.:--::....::::...... .:....    .  ..   :.      ..         ..      
                              .:::---=------:::--:-=:::---=--=--:..-::::....::.....::.:....................        ...    ...     
                                :::-=---------------=--===---==--::-==---=+::--:........:.......::-:............... ..........    
                                 .::====--------------:-===-::==--::-====-:::----::-+*+..-=-..........-:.......:--==:.............
                                    -=*#==-=--=-------::-=+======-::.:::--:::::=++==-::..:::::::-=+:::...--:............:.........
                                      .-+*+==--=----=------=+++===+++--===-:-::::=::::::..:-=+=--:::::...-:-::::::-::::...::::--::
                                        ..:=*++=----=-::::-=++**:===+**+========::----::::::::=+:::......:-:-::::::::::---::-#=:::
                                           .:::*#+++:----:......:-+**++**-.:+****+=====+==--::::::..::::::::-:-=-:::::::.....=::::
                                               .:-=---::::::.:::-:...:..  :-****++*==-----=++=-:-----:-::::::..:-:::::::......-:::
                                                    :+=-::..:::.:.... ..::-.....-==++=::**###*+=------------:::::-::::::::::::::.:
                                                     ..::.:.. ...    .:::..:..........::-*#+=-=----::::::------:::------:::::...::
                                                         .=..      :   .  ..:::. .::::::........:----:.-+++++=---:-----------::::.
                                                             ... ...             ::.......... ........:=-===--------:::::::::::...
                                                                             .        .......   .:..:::.... ..:::-----:....:------
                                                                        .... .                .:.........................:=+------
                                                                                          .        ..........       :-:::: ..:----    
                """;
        FRAME[102] = """
                                                                                                                                              
                                                                                                                                  
                                                                                                                                  
                                                           .......                                                                
                                            .....         ................ .....                                                  
                                            ....         ........................     .                                           
                                                       ...................:.::.............                                       
                                                      ......::::::........::::::.:...........                                     
                                                     .........:::::::.:::::......::..:::....                                      
                                           ...  . ..........::::::::::::::.::::::::::::::::.                                      
                                        ..      ...............::::::::::::::::::::::::......                                     
                                          .......................:::::::::::::::.:.........                                       
                                   . ..............................::::::::............                                           
                                 ................................................                                                 
                               ..:....................            ...........                     .                     .         
                           ....-:::===-:..............      ..        ::. ..       -.              :...                ......     
                           ..::.:::==-:===::....::... .:      +:        +::         ::..            :.....              .... ..:  
                          ....:--::-==-::--=--:...-:.   -:.    =-::      ==...       :=:..:.         :.:...:.            .  :....:
                         .:....-=:-::-==:::--::::..:--.  :-::   ==:.::    ++:..::     -=:...:-       .:.......-.         ..  .....
                         ..--:....-----+=::::--::--:.:--: .:=--. ---+-+=.  ==-:::--.  .=-::...:-:     ::..:.  ..--       ..   .:  
                          ..:--....::::-**:::.-=::.+-.:.:=..::-*=.--:==-**=--=:::::*+=--=---::..-+-.. -:::.::... :==    ::......-.
                            ..:-:.....:--+*=::. ..:....:..-..:..:-.:::...:*---:.:....*+-=-:.:.....-#=--::...:.     :#*===:....  .:
                              .:--:..:=+---++::.:=-::::::---::====-: :=-:. .  ..:.    :      .      -.       .       :+=-.       .
                               ..-- .-=----:.::---==-------::-:--:--:.:=-:..:-:---::......::::...    ..:.    ..        .          
                                 ..===::.  .:.-=-==--:-=--=--===---======--=--:..:......::.:::..... .==:...........    .::...     
                                  .-:.... ... ...=-=-=+=--::::----:-====:--===--::-====+===: .::........ .:......  ..:-=::........
                                     ... .    .......:-=-=+=-:-----=-==----::=---:::-=++=-::..----:-===+=:. :::........::..:......
                                             ..:::::-=:---.:--::::::------===-==+=-:.:::::::::.:=+++==-:::..:-------==::--:...::..
                                              .:::::-=. :--*+::::=:::-:..:-=++=..-====-===++:--...:::.:::::...-:+==--:::::::::::++
                                                 .:::-       ...:..::::::.......:==++***+-:-------.:::-:::::::::.:=+=-::........:-
                                                   ..          .:...... .  --:::::.....:::=--+++**++-=--====-:::..:::......::.....
                                                                    .:...... ....:--.  ..:--.::=*+++++=:::::::----:::::::::::::::.
                                                                         ...   .    .:--:::::......... :::::**##**+--:---------:::
                                                                            .... ..        .:::..    ..:--:.:+++==------::::::::::
                                                                                                   -::.::::..   .. .:::---:....:==
                                                                                      .:.......       ..........   ......    .=--+
                                                                                                              ..:..   ::..:::- ...    
                """;
        FRAME[103] = """
                                                                                                                                              
                                                                                                                                  
                                                                                                                                  
                                                           .......                                                                
                                            .....         ................ .....                                                  
                                            ....         ........................     .                                           
                                                       ...................:.::.............                                       
                                                      ......::::::........::::::.:...........                                     
                                                     .........:::::::.:::::......::..:::....                                      
                                           ...  . ..........::::::::::::::.::::::::::::::::.                                      
                                        ..      ...............::::::::::::::::::::::::......                                     
                                          .......................:::::::::::::::.:.........                                       
                                   . ..............................::::::::............                                           
                                 ................................................                                                 
                               ..:....................            ...........                     .                     .         
                           ....-:::===-:..............      ..        ::. ..       -.              :...                ......     
                           ..::.:::==-:===::....::... .:      +:        +::         ::..            :.....              .... ..:  
                          ....:--::-==-::--=--:...-:.   -:.    =-::      ==...       :=:..:.         :.:...:.            .  :....:
                         .:....-=:-::-==:::--::::..:--.  :-::   ==:.::    ++:..::     -=:...:-       .:.......-.         ..  .....
                         ..--:....-----+=::::--::--:.:--: .:=--. ---+-+=.  ==-:::--.  .=-::...:-:     ::..:.  ..--       ..   .:  
                          ..:--....::::-**:::.-=::.+-.:.:=..::-*=.--:==-**=--=:::::*+=--=---::..-+-.. -:::.::... :==    ::......-.
                            ..:-:.....:--+*=::. ..:....:..-..:..:-.:::...:*---:.:....*+-=-:.:.....-#=--::...:.     :#*===:....  .:
                              .:--:..:=+---++::.:=-::::::---::====-: :=-:. .  ..:.    :      .      -.       .       :+=-.       .
                               ..-- .-=----:.::---==-------::-:--:--:.:=-:..:-:---::......::::...    ..:.    ..        .          
                                 ..===::.  .:.-=-==--:-=--=--===---======--=--:..:......::.:::..... .==:...........    .::...     
                                  .-:.... ... ...=-=-=+=--::::----:-====:--===--::-====+===: .::........ .:......  ..:-=::........
                                     ... .    .......:-=-=+=-:-----=-==----::=---:::-=++=-::..----:-===+=:. :::........::..:......
                                             ..:::::-=:---.:--::::::------===-==+=-:.:::::::::.:=+++==-:::..:-------==::--:...::..
                                              .:::::-=. :--*+::::=:::-:..:-=++=..-====-===++:--...:::.:::::...-:+==--:::::::::::++
                                                 .:::-       ...:..::::::.......:==++***+-:-------.:::-:::::::::.:=+=-::........:-
                                                   ..          .:...... .  --:::::.....:::=--+++**++-=--====-:::..:::......::.....
                                                                    .:...... ....:--.  ..:--.::=*+++++=:::::::----:::::::::::::::.
                                                                         ...   .    .:--:::::......... :::::**##**+--:---------:::
                                                                            .... ..        .:::..    ..:--:.:+++==------::::::::::
                                                                                                   -::.::::..   .. .:::---:....:==
                                                                                      .:.......       ..........   ......    .=--+
                                                                                                              ..:..   ::..:::- ...    
                """;
        FRAME[104] = """
                                                                                                                                              
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                               .. ....  .  ..                                                     
                                                           .....................                                                  
                                                         ..............................                                           
                                                        .....................................                                     
                                                      .......................................                                     
                                                 ............................................                                     
                                               . ............................................                                     
                                              ............................................                                        
                                       ........ .........   ................ ..                                                   
                                 .....................           ........ .                                                       
                            .  ......................              .....                                                          
                            .......................                                                                               
                           .......................                                                                                
                          ......................                                                                                  
                          .....................                                                                                   
                           .................                                                .-.   .                          ..:::
                            ..............                                                     -==-...                            
                             ............                 ....                                   .-===-:..                        
                                  .  ...                  .. ..                                     :=======::.                   
                                                                                                      .-=========++===            
                                                                                .                        :===++++****####**+++===-
                                                                     -----::::::::::::..                     =**###*##***++===----
                                                                        .:::::::::--:::::::---::.                -+***+++==-------
                                                                            .:::::-----:.::::::::---.                .-======-----
                                                                                 ::-------.:.:::::::::--:                       ..
                                                                                     :------:..........::::.                      
                                                                                         :-----:.........::::..                   
                                                                                            .:---:........:::--:.                 
                                                                                                .---::......::-----               
                                                                                                   ---::.....:::------.           
                                                                                                     :---::....::------=**-.      
                                                                                                       .---::....::-----==*##*+:  
                                                                                                         .----::..:::----===*****+
        """;
        FRAME[105] = """
                                                                                                                                              
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                               .           .                                                      
                                                              ...    .    ......                                                  
                                                             .....   .....  .....    .                                            
                                                            .............. .................                                      
                                                       ......................................                                     
                                                  .......................................  ..                                     
                                                  ......................................                                          
                                                ............ ......................                                               
                                       ....     ....         ... ..........                                                       
                                 ...........                      .......                                                         
                               .................. ...                 .                                                           
                            ....................                                                                                  
                          .......................                                                                                 
                           ....................                                                                                   
                           ..................                                                                                     
                            ...............                                                                                       
                                ........                                                                                          
                               ........                   .. .                                                                    
                                                          .. .                                                                    
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                     .:-----------
                                                                                                                           .------
                                                                                                                                .:
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                                                                                                                                      
                """;
        FRAME[106] = """
                                                                                                                                              
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                               ..                                                 
                                                               .....   .       ..                                                 
                                                        .     .......  ....     ....                                              
                                                        ..     .......................                                            
                                                   ...   .     .................. ..                                              
                                                                 ..  ......                                                       
                                    .                                ..                                                           
                                 ..... ...                                                                                        
                                .............                                                                                     
                                ................                                                                                  
                            ...................                                                                                   
                            ................                                                                                      
                            ..............                                                                                        
                                .......                                                                                           
                                ........                                                                                          
                                 .....                    .. .                                                                    
                                                          .. .                                                                    
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                      
                """;
        FRAME[107] = """
                                                                                                                                              
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                ..                                                                
                                                                ..                                                                
                                                                ..    ....                                                        
                                                                      ......                                                      
                                                                  .   .                                                           
                                                                                                                                  
                                   ..                                                                                             
                                  .....                                                                                           
                                ..........                                                                                        
                              ..............                                                                                      
                            ...............                                                                                       
                                ........                                                                                          
                                   ...                                                                                            
                                  ....                                                                                            
                                                          .. .                                                                    
                                                          .. .                                                                    
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                     
                """;
        FRAME[108] = """
                                                                                                                                              
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                 ...                                                                                              
                             ..     ....                                                                                          
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                          .. .                                                                    
                                                          .. .                                                                    
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                      
                """;
        FRAME[109] = """
                                                                                                                                              
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                          .. .                                                                    
                                                          .. .                                                                    
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                      
                """;
        FRAME[110] = """
                                                                                                                                              
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                          .. .                                                                    
                                                          .. .                                                                    
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                  
                                                                                                                                      
                """;
    }

    // Clear console between frames
    static void clearConsole() {
        try {
            // Use ANSI escape codes to clear screen and move cursor to top-left
            // Sequence: ESC[2J clears screen, ESC[H moves cursor to home
            System.out.print("\u001B[2J\u001B[H");
            System.out.flush();
        } catch (Exception e) {
            // Fallback: print many blank lines to clear visible area
            for (int i = 0; i < 100; i++) {
                System.out.println();
            }
            System.out.flush();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // Enable ANSI support
        try {
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                // Enable ANSI escape sequences
                ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "echo", "");
                pb.start();
            }
        } catch (Exception e) {
            // Ignore if ANSI enabling fails
        }

        int delayMs = 40;
        for (int i = 0; i < FRAME.length; i++) {
            if (FRAME[i] == null) continue;

            // Clear console first
            clearConsole();
            
            // Print frame - frames already contain newlines from text blocks
            System.out.print(YELLOW + FRAME[i] + RESET);
            System.out.flush();
            
            // Delay to show frame before moving to next
            Thread.sleep(delayMs);
        }

        // Small delay to show the last frame before clearing
        Thread.sleep(delayMs);
        clearConsole();
        System.out.flush();

        System.out.println(ColorUtils.header("======================================="));
        System.out.println(ColorUtils.header("   ROLE-BASED CONTACT MANAGEMENT APP   "));
        System.out.println(ColorUtils.header("=======================================\n"));

        // Test DB connection
        if (db.DBConnection.getConnection() != null) {
            System.out.println(ColorUtils.success("Database connection successful.\n"));
        } else {
            System.out.println(ColorUtils.error("Database connection failed. Exiting..."));
            return;
        }

        // Start login flow
        service.AuthService auth = new service.AuthService();
        auth.startLogin();
    }
}
