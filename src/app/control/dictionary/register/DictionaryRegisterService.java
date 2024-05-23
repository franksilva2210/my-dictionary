package app.control.dictionary.register;

import app.util.ModPersistData;
import app.util.ValidateControlFx;

public class DictionaryRegisterService {
	
	public void validateFields(DictionaryRegisterComponentsFxDto componentsFxDto) throws Exception {
		ValidateControlFx validateFX = new ValidateControlFx();
		validateFX.setControl(componentsFxDto.getTxtTitle());
		validateFX.setError(false);
		validateFX.validateControl();
		
		if (validateFX.getError()) {
			throw new Exception("Campo Titulo invalido.");
		}
		
		validateFX.setControl(componentsFxDto.getTxtLanguage());
		validateFX.setError(false);
		validateFX.validateControl();
		
		if (validateFX.getError()) {
			throw new Exception("Campo Lingua invalido.");
		}
	}
	
	protected void executePersistence(ModPersistData modPersist, Dictionary dictionary) throws Exception {
		DictionaryDao dictionaryDao = new DictionaryDao();
		
		switch(modPersist) {
			case INSERT:
				try {
					dictionaryDao.save(dictionary);
				} catch (Exception e) {
					throw new Exception("Falha ao salvar registro");
				}
				break;
			
			case UPDATE:
				try {
					dictionaryDao.update(dictionary);
				} catch (Exception e) {
					throw new Exception("Falha ao atualizar registro");
				}
				break;
				
			case DELETE:
				try {
					dictionaryDao.delete(dictionary);
				} catch (Exception e) {
					throw new Exception("Falha ao remover registro");
				}
				break;
			
			default:
				break;
		}
	}
}
