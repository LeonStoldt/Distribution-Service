Der Distributor nimmt Requestdetails von dem Connector an und kümmert sich um das User-Management.
Falls die Anfrage an weitere Services notwendig ist, leitet der Distributor die Parameter an den richtigen Service weiter und schickt die erhaltene Antwort an den Connector zurück. 

## Wie binde ich weitere Services an den Distributor an?
1.  Microservice (z.B. Spring Boot Service) aufsetzen (egal welche Programmiersprache) und mit der gewünschten Funktion (z.B. Api-Anfrage) ausstatten
2.  Für java z.B. folgenden Rest-Controller erstellen:
    ``` java
    @RestController
    public class Controller {
    
        // handles api requests or provides features
        // input - message from telegram without keyword
        // output - message to response user request
        private static final String JSON = MediaType.APPLICATION_JSON_VALUE;
        private final ApiService apiService;
    
        @Autowired
        public Controller(ApiService apiService) {
            this.apiService = apiService;
        }
    
        // (optional) to check status of service via browser
        @ResponseStatus(HttpStatus.OK)
        @GetMapping(value = "/")
        public String getStatus() {
            return "xxxService is active.";
        }
    
        @PostMapping(value = "/api", produces = JSON, consumes = JSON) //define endpoint
        public ResponseEntity<String> receiveRequest(@RequestBody String message) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(apiService.getResponseMessage(message));
        }
        //return ResponseEntity<String> with body filled with the response-message
    }
    ```
3. Die folgenden Anpassungen müssen im Distributor vorgenommen werden:
    -   `Command` Eintrag mit Kommando hinzufügen `COMMAND("description.command.serviceName", "/command"),`
    -   `ResponseService` Eintrag hinzufügen: `SERVICENAME("http://domain:8080", Command.COMMAND)`
    -   `userFeedback.properties` Beschreibung des Befehls eintragen unter `# command descriptions` (Muster: `description.command.serviceName=[Beschreibung des Befehls]`)
    -   `CommandTest.commandListIsGeneratedCorrectly()` Den erwarteten generierten Hilfe Text anpassen.