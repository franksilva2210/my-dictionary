package app.control.word.register;

import app.util.ModPersistData;
import app.util.ValidateControlFx;

public class WordRegisterService {
	
	public void validateFields(WordRegisterComponentsDto componentsFXDto) throws Exception {
		ValidateControlFx validateFX = new ValidateControlFx();
		validateFX.setControl(componentsFXDto.getTxtDescriptionWord());
		validateFX.setError(false);
		validateFX.validateControl();
		
		if (validateFX.getError()) {
			throw new Exception("Campo Descricao invalido");
		}
		
		validateFX.setControl(componentsFXDto.getTxtTranslationWord());
		validateFX.setError(false);
		validateFX.validateControl();
		
		if (validateFX.getError()) {
			throw new Exception("Campo Traducao invalido");
		}
	}
	
	protected void executePersistence(ModPersistData mod, Word word) throws Exception {
		WordDao WordDao = new WordDao();
		
		switch(mod) {
			case INSERT: {
				try {
					WordDao.save(word);
				} catch (Exception e) {
					throw new Exception("Falha ao salvar registro.");
				}
				break;
			}
			
			case UPDATE: {
				try {
					WordDao.update(word);
				} catch (Exception e) {
					throw new Exception("Falha ao atualizar registro.");
				}
				break;
			}
			
			case DELETE: {
				try {
					WordDao.delete(word);
				} catch (Exception e) {
					throw new Exception("Falha ao remover registro.");
				}
				break;
			}
			
			default:
				break;
		}
	}
}
