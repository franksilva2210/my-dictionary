package app.control.word.register;

public class Word {
	
	private Integer id;
	private String description;
	private String translation;
	private String classGramatic;
	private String signification;
	
	public Word() {
		this.id = 0;
		this.description = new String();
		this.translation = new String();
		this.classGramatic = new String();
		this.signification = new String();
	}
	
	public Word(Integer id, String description, String translation, String classGramatic, String signification) {
		this.id = id;
		this.description = description;
		this.translation = translation;
		this.classGramatic = classGramatic;
		this.signification = signification;
	}
	
	public Word(Word word) {
		this.id = word.getId();
		this.description = word.getDescription();
		this.translation = word.getTranslation();
		this.classGramatic = word.getClassGramatic();
		this.signification = word.getSignification();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTranslation() {
		return translation;
	}

	public void setTranslation(String translation) {
		this.translation = translation;
	}

	public String getClassGramatic() {
		return classGramatic;
	}

	public void setClassGramatic(String classGramatic) {
		this.classGramatic = classGramatic;
	}

	public String getSignification() {
		return signification;
	}

	public void setSignification(String signification) {
		this.signification = signification;
	}
	
	public boolean existUpdate(Word word) {
		if (this.id.equals(word.getId())) {
			if (!this.description.equals(word.getDescription())) {
				return true;
			} else if (!this.translation.equals(word.getTranslation())) {
				return true;
			} else if (!this.classGramatic.equals(word.getClassGramatic())) {
				return true;
			} else if(!this.signification.equals(word.getSignification())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
}