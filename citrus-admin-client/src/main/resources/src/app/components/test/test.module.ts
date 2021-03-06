import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {ServiceModule} from "../../service/service.module";
import {TestsComponent} from "./tests.component";
import {TestEditorComponent} from "./test.editor.component";
import {TestListComponent} from "./test.list.component";
import {SourceCodeComponent} from "./detail/sources/source.code.component";
import {TestDetailComponent} from "./detail/test.detail.component";
import {TestGroupRunComponent} from "./test.group.run.component";
import {TestListGroupComponent} from "./test.listgroup.component";
import {TestMessageListComponent} from "./detail/message/test.message.list.component";
import {TestMessageComponent} from "./detail/message/test.message.component";
import {TestProgressComponent} from "./detail/results/test.progress.component";
import {UtilComponentsModule} from "../util/util.module";
import {TestTransitionComponent} from "./detail/designer/test.transition.component";
import {TestDesignerComponent} from "./detail/designer/test.designer.component";
import {TestContainerComponent} from "./detail/designer/test.container.component";
import {TestActionComponent} from "./detail/designer/test.action.component";
import {TestResultComponent} from "./detail/results/test.result.component";
import {UtilModule} from "../../util/util.module";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {TestRoutingModule} from "./test-routing.module";
import {TestStateActions, TestStateEffects, TestStateService} from "./test.state";
import {EffectsModule} from "@ngrx/effects";
import {TestActionFormComponent} from "./detail/designer/test.action.form.component";

const components = [
    TestsComponent,
    TestEditorComponent,
    TestListComponent,
    SourceCodeComponent,
    TestDetailComponent,
    TestGroupRunComponent,
    TestListGroupComponent,
    TestMessageListComponent,
    TestMessageComponent,
    TestProgressComponent,
    TestActionComponent,
    TestActionFormComponent,
    TestContainerComponent,
    TestDesignerComponent,
    TestTransitionComponent,
    TestResultComponent
];

@NgModule({
    imports: [
        UtilComponentsModule,
        UtilModule,
        FormsModule,
        CommonModule,
        ServiceModule,
        TestRoutingModule,
        EffectsModule.forFeature([TestStateEffects]),
        ReactiveFormsModule
    ],
    providers: [
        TestStateActions,
        TestStateService,
        TestStateEffects
    ],
    declarations: components,
    exports: components
})
export class TestModule {
}
