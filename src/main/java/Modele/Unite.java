package Modele;

    public class Unite {

        private int idUnite;
        private String nomUnite;

        // Constructeur vide
        public Unite() {
        }

        // Constructeur avec tous les attributs
        public Unite(int idUnite, String nomUnite) {
            this.idUnite = idUnite;
            this.nomUnite = nomUnite;
        }

        // Getters
        public int getIdUnite() {
            return idUnite;
        }

        public String getNomUnite() {
            return nomUnite;
        }

        // Setters
        public void setIdUnite(int idUnite) {
            this.idUnite = idUnite;
        }

        public void setNomUnite(String nomUnite) {
            this.nomUnite = nomUnite;
        }
    }


