package org.multij.model.analysis;

import org.multij.Module;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

public class ModuleBinding extends AnalysisBase implements ModuleBindingAnalysis {

	public ModuleBinding(ProcessingEnvironment processingEnv) {
		super(processingEnv);
	}

	@Override
	public boolean check(ExecutableElement methodRef) {
		TypeMirror type = methodRef.getReturnType();
		if (type.getKind() == TypeKind.DECLARED) {
			DeclaredType declared = (DeclaredType) type;
			Element element = declared.asElement();
			if (element.getAnnotation(Module.class) != null) {
				return true;
			}
		}
		messager().printMessage(Diagnostic.Kind.ERROR, "Module reference must refer to a type that is declared as a @Module.", methodRef);
		return false;
	}
}
