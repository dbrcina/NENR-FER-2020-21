Requirements: Java v15, Maven v3.6.3

1) Pozicionirati se u root direktorij - direktorij u kojem se nalazi src folder i pom.xml datoteka
2) Izvrsiti naredbu mvn compile
3) Izvrsiti naredbu java -cp target/classes hr.fer.zemris.nenr.hw07.Demo -params
		params: dataset_file, architecture, save_file
	npr. java -cp target/classes hr.fer.zemris.nenr.hw07.Demo zad7-dataset.txt 2x8x3 params.txt