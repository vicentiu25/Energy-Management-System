export const environment = {
  production: false,
  user_api: {
    base: 'http://localhost:8081'
  },
  device_api: {
    base: 'http://localhost:8082'
  },
  monitoring_api: {
    base: 'http://localhost:8083'
  },
  chat_api: {
    base: 'http://localhost:8084'
  },
  jwt: {
    tokenKey: 'token',
    allowedDomains: ['http://localhost:8081'],
    disallowedRoutes: ['http://localhost:8081/user/login', 'http://localhost:8081/user/register']
  }
};
