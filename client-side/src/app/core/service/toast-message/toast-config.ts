import { IndividualConfig } from 'ngx-toastr';

export const toastConfig: Partial<IndividualConfig> = {
    positionClass: 'toast-top-full-width',
    timeOut: 4000,
    easeTime: 900,
    onActivateTick: true,
    newestOnTop: true,
};