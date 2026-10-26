import type { RouteObject } from 'react-router-dom';
import { complianceRuleRoutes } from './compliance-rule/routes';
import { complianceCheckRoutes } from './compliance-check/routes';
import { auditRecordRoutes } from './audit-record/routes';
import { complianceExceptionRoutes } from './compliance-exception/routes';
import { policyDocumentRoutes } from './policy-document/routes';

export const complianceDomainRoutes: RouteObject[] = [
  ...complianceRuleRoutes,
  ...complianceCheckRoutes,
  ...auditRecordRoutes,
  ...complianceExceptionRoutes,
  ...policyDocumentRoutes,
].map((route) => ({ ...route, path: `compliance/${route.path}` }));
