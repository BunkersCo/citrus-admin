const LOCAL_STORAGE_ENV_KEY = '$CITRUS_ADMIN';

let userEnv = {};
if(localStorage) {
    const localStorageContent = localStorage.getItem(LOCAL_STORAGE_ENV_KEY) + '';
    try {
        userEnv = JSON.parse(localStorageContent) || {};
    } catch(e) {
        console.warn(`Found item in '${LOCAL_STORAGE_ENV_KEY}' but could not parse it's contents: `, localStorageContent, e)
    }
}

export interface Environment {
    production?:boolean;
    traceRouting?:boolean;
    reduxTools?:boolean;
    stompDebug?:boolean;
}

export const environment:Environment = {
    production: false,
    traceRouting: false,
    reduxTools: true,
    stompDebug:true,
    ...userEnv
};

