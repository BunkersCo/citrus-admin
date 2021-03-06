import {Component, OnInit, Input} from '@angular/core';
import {TestDetail, TestResult} from "../../../model/tests";
import {Alert} from "../../../model/alert";
import {AlertService} from "../../../service/alert.service";
import {Subscription} from "rxjs";
import {LoggingService} from "../../../service/logging.service";
import {TestService} from "../../../service/test.service";
import {SocketEvent} from "../../../model/socket.event";

@Component({
    selector: "test-detail",
    templateUrl: 'test-detail.html'
})
export class TestDetailComponent implements OnInit {

    @Input() detail: TestDetail;

    constructor(private _alertService: AlertService,
                private _testService: TestService,
                private loggingService: LoggingService) {}

    private loggingOutputSubscription: Subscription;
    private loggingEventSubscription: Subscription;

    completed = 0;
    failed = false;

    finishedActions = 0;

    logs = "";
    loggingFrame = "";

    ngOnInit() {
        this.loggingOutputSubscription = this.loggingService.logOutput
            .subscribe((e: SocketEvent) => {
                jQuery('pre.logger').scrollTop(jQuery('pre.logger')[0].scrollHeight);
                this.logs += e.msg;
                this.loggingFrame = e.msg;
                this.handle(e);
            });

        this.loggingEventSubscription = this.loggingService.testEvents
            .subscribe(this.handle);
    }

    ngOnDestroy(): void {
        if(this.loggingOutputSubscription) {
            this.loggingOutputSubscription.unsubscribe();
        }

        if(this.loggingEventSubscription) {
            this.loggingEventSubscription.unsubscribe();
        }
    }

    execute() {
        this.logs = "";
        this.loggingFrame = "";
        this.failed = false;
        this.completed = 0;
        this.finishedActions = 0;
        this.detail.messages = [];
        this.detail.running = true;
        this.detail.result = undefined;
        this._testService.execute(this.detail)
            .subscribe(
                processId => {
                    this.detail.result = new TestResult();
                    this.detail.result.test = this.detail;
                    this.detail.result.processId = processId;
                },
                error => this.notifyError(<any>error));
    }

    handle(event: SocketEvent) {
        console.log('Handle', event);
        if ("PROCESS_START" == event.type) {
            this.completed = 1;
        } else if ("TEST_START" == event.type) {
            this.completed = 1;
        } else if ("TEST_ACTION_FINISH" == event.type) {
            this.finishedActions++;

            if (this.detail.actions.length) {
                this.completed = Math.round((this.finishedActions / this.detail.actions.length) * 100);
            } else if (this.completed < 90) {
                this.completed += 2;
            }
        } else if ("TEST_FAILED" == event.type || "PROCESS_FAILED" == event.type) {
            this.failed = true;
            if (this.detail.result) {
                this.detail.result.status = 'FAIL';
            }
        } else if ("TEST_SUCCESS" == event.type || "PROCESS_SUCCESS" == event.type) {
            this.failed = false;
            if (this.detail.result) {
                this.detail.result.status = 'PASS';
            }
        } else if ("TEST_SKIP" == event.type) {
            this.failed = false;
            if (this.detail.result) {
                this.detail.result.status = 'SKIP';
            }
        } else {
            if (this.completed < 11) {
                this.completed++;
            }
        }

        if ("PROCESS_FAILED" == event.type || "PROCESS_SUCCESS" == event.type) {
            this.completed = 100;
            this.detail.running = false;
            this.loggingFrame = this.logs;
            jQuery('pre.logger').scrollTop(jQuery('pre.logger')[0].scrollHeight);
        }
    }

    hasResult() {
      return this.detail.result != undefined;
    }

    notifyError(error: any) {
        this._alertService.add(new Alert("danger", JSON.stringify(error), false));
    }
}

