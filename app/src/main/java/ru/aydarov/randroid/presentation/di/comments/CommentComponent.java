package ru.aydarov.randroid.presentation.di.comments;

import dagger.Subcomponent;
import ru.aydarov.randroid.presentation.ui.comments.CommentsFragment;


/**
 * @author Aydarov Askhar 2020
 */

@CommentScope
@Subcomponent(modules = {NetworkModuleComment.class, InteractorModuleComment.class, RepositoryModuleComment.class})
public interface CommentComponent {
    void inject(CommentsFragment fragment);

    @Subcomponent.Builder
    interface Builder{
        CommentComponent.Builder networkModule(NetworkModuleComment module);
        CommentComponent.Builder repositoryModule(RepositoryModuleComment module);
        CommentComponent.Builder interactorModule(InteractorModuleComment module);
        CommentComponent build();

    }

}
