import type { LifecycleStatus, SortState } from '@/shared/types';

export const POLICY_DOCUMENT_LIFECYCLE_STAGE_VALUES = ['PLAN', 'BUILD', 'OPERATE', 'RETIRE'] as const;
export type PolicyDocumentLifecycleStage = (typeof POLICY_DOCUMENT_LIFECYCLE_STAGE_VALUES)[number];
export const POLICY_DOCUMENT_LIFECYCLE_STAGE_LABELS: Record<PolicyDocumentLifecycleStage, string> = {
  PLAN: 'Plan',
  BUILD: 'Build',
  OPERATE: 'Operate',
  RETIRE: 'Retire',
};
export const POLICY_DOCUMENT_LIFECYCLE_STAGE_OPTIONS = POLICY_DOCUMENT_LIFECYCLE_STAGE_VALUES.map((value) => ({ value, label: POLICY_DOCUMENT_LIFECYCLE_STAGE_LABELS[value] }));

export const POLICY_DOCUMENT_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type PolicyDocumentCriticality = (typeof POLICY_DOCUMENT_CRITICALITY_VALUES)[number];
export const POLICY_DOCUMENT_CRITICALITY_LABELS: Record<PolicyDocumentCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const POLICY_DOCUMENT_CRITICALITY_OPTIONS = POLICY_DOCUMENT_CRITICALITY_VALUES.map((value) => ({ value, label: POLICY_DOCUMENT_CRITICALITY_LABELS[value] }));

export interface PolicyDocument {
  id: string;
  tenantId: string;
  version: number;
  createdAt?: string;
  updatedAt?: string;
  createdBy?: string;
  updatedBy?: string;
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  title: string;
  docVersion: string;
  effectiveDate: string;
  owner?: string;
  documentUrl?: string;
  lifecycleStage?: PolicyDocumentLifecycleStage;
  sourceSystem?: string;
  region?: string;
  criticality?: PolicyDocumentCriticality;
  costCenter?: string;
}

export interface PolicyDocumentInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  title: string;
  docVersion: string;
  effectiveDate: string;
  owner?: string;
  documentUrl?: string;
  lifecycleStage?: PolicyDocumentLifecycleStage;
  sourceSystem?: string;
  region?: string;
  criticality?: PolicyDocumentCriticality;
  costCenter?: string;
}

export type PolicyDocumentFormValues = Partial<PolicyDocumentInput>;

export interface PolicyDocumentUpdateInput extends Partial<PolicyDocumentInput> {
  expectedVersion?: number;
}

export interface PolicyDocumentFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  title?: string;
  docVersion?: string;
  effectiveDateFrom?: string;
  effectiveDateTo?: string;
  owner?: string;
  documentUrl?: string;
  lifecycleStage?: PolicyDocumentLifecycleStage[];
  sourceSystem?: string;
  region?: string;
  criticality?: PolicyDocumentCriticality[];
  costCenter?: string;
}

export type PolicyDocumentSort = SortState<keyof PolicyDocument & string>;

export interface PolicyDocumentSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const POLICYDOCUMENT_PATH = '/api/v1/policy-documents';
export const POLICYDOCUMENT_TITLE = 'Policy Document';
export const POLICYDOCUMENT_TITLE_PLURAL = 'Policy Documents';

export const POLICYDOCUMENT_DEFAULTS: PolicyDocumentFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  title: '',
  docVersion: '',
};
