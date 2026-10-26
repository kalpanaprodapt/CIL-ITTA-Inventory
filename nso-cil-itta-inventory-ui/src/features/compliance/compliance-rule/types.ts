import type { LifecycleStatus, SortState } from '@/shared/types';

export const COMPLIANCE_RULE_FRAMEWORK_VALUES = ['ISO27001', 'SOC2', 'PCI', 'NIST', 'INTERNAL'] as const;
export type ComplianceRuleFramework = (typeof COMPLIANCE_RULE_FRAMEWORK_VALUES)[number];
export const COMPLIANCE_RULE_FRAMEWORK_LABELS: Record<ComplianceRuleFramework, string> = {
  ISO27001: 'Iso27001',
  SOC2: 'Soc2',
  PCI: 'Pci',
  NIST: 'Nist',
  INTERNAL: 'Internal',
};
export const COMPLIANCE_RULE_FRAMEWORK_OPTIONS = COMPLIANCE_RULE_FRAMEWORK_VALUES.map((value) => ({ value, label: COMPLIANCE_RULE_FRAMEWORK_LABELS[value] }));

export const COMPLIANCE_RULE_SEVERITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type ComplianceRuleSeverity = (typeof COMPLIANCE_RULE_SEVERITY_VALUES)[number];
export const COMPLIANCE_RULE_SEVERITY_LABELS: Record<ComplianceRuleSeverity, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const COMPLIANCE_RULE_SEVERITY_OPTIONS = COMPLIANCE_RULE_SEVERITY_VALUES.map((value) => ({ value, label: COMPLIANCE_RULE_SEVERITY_LABELS[value] }));

export const COMPLIANCE_RULE_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type ComplianceRuleCriticality = (typeof COMPLIANCE_RULE_CRITICALITY_VALUES)[number];
export const COMPLIANCE_RULE_CRITICALITY_LABELS: Record<ComplianceRuleCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const COMPLIANCE_RULE_CRITICALITY_OPTIONS = COMPLIANCE_RULE_CRITICALITY_VALUES.map((value) => ({ value, label: COMPLIANCE_RULE_CRITICALITY_LABELS[value] }));

export interface ComplianceRule {
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
  ruleCode: string;
  framework?: ComplianceRuleFramework;
  severity?: ComplianceRuleSeverity;
  expression?: string;
  region?: string;
  owner?: string;
  criticality?: ComplianceRuleCriticality;
  costCenter?: string;
  notes?: string;
}

export interface ComplianceRuleInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  ruleCode: string;
  framework?: ComplianceRuleFramework;
  severity?: ComplianceRuleSeverity;
  expression?: string;
  region?: string;
  owner?: string;
  criticality?: ComplianceRuleCriticality;
  costCenter?: string;
  notes?: string;
}

export type ComplianceRuleFormValues = Partial<ComplianceRuleInput>;

export interface ComplianceRuleUpdateInput extends Partial<ComplianceRuleInput> {
  expectedVersion?: number;
}

export interface ComplianceRuleFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  ruleCode?: string;
  framework?: ComplianceRuleFramework[];
  severity?: ComplianceRuleSeverity[];
  region?: string;
  owner?: string;
  criticality?: ComplianceRuleCriticality[];
  costCenter?: string;
}

export type ComplianceRuleSort = SortState<keyof ComplianceRule & string>;

export interface ComplianceRuleSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const COMPLIANCERULE_PATH = '/api/v1/compliance-rules';
export const COMPLIANCERULE_TITLE = 'Compliance Rule';
export const COMPLIANCERULE_TITLE_PLURAL = 'Compliance Rulees';

export const COMPLIANCERULE_DEFAULTS: ComplianceRuleFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  ruleCode: '',
};
