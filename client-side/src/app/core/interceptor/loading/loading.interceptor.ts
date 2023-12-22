import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpResponse
} from '@angular/common/http';
import { finalize, Observable, tap } from 'rxjs';
import { LoadingService } from '../../service/loading/loading.service';

@Injectable()
export class LoadingInterceptor implements HttpInterceptor {

  private requests: HttpRequest<any>[] = [];

  constructor(private loadingService: LoadingService) { }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    this.requests.push(request);
    this.loadingService.show();

    return next.handle(request).pipe(
      finalize(() => {
        const index = this.requests.indexOf(request);
        if (index !== -1) {
          this.requests.splice(index, 1);
        }

        if (this.requests.length === 0) {
          this.loadingService.hide();
        }
      })
    );
  }
}
