import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { COMPLIANCE_RULE_FRAMEWORK_OPTIONS, COMPLIANCE_RULE_SEVERITY_OPTIONS, COMPLIANCE_RULE_CRITICALITY_OPTIONS, ComplianceRuleFramework, ComplianceRuleSeverity, ComplianceRuleCriticality } from './types';
import { validateComplianceRule, cleanComplianceRuleValues } from './schema';
import { COMPLIANCERULE_DEFAULTS } from './types';
import type { ComplianceRuleFormValues, ComplianceRuleInput } from './types';

interface Props {
  initial?: ComplianceRuleFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: ComplianceRuleInput) => void;
  onCancel?: () => void;
}

export function ComplianceRuleForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<ComplianceRuleFormValues>({ ...COMPLIANCERULE_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof ComplianceRuleFormValues>(key: K, value: ComplianceRuleFormValues[K]) {
    setValues((prev) => ({ ...prev, [key]: value }));
    if (errors[key as string]) {
      setErrors((prev) => {
        const next = { ...prev };
        delete next[key as string];
        return next;
      });
    }
  }

  function submit(ev: React.FormEvent) {
    ev.preventDefault();
    const result = validateComplianceRule(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanComplianceRuleValues(values as Record<string, unknown>) as unknown as ComplianceRuleInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Compliance Rule form">
      {serverError ? <div role="alert" className="form-error">{serverError}</div> : null}
      <FormField label="Code" htmlFor="code" error={errors.code} required={true}>
        <Input
          id="code"
          value={values.code ?? ''}
          maxLength={255}
          onChange={(v) => set('code', v)}
        />
      </FormField>
      <FormField label="Name" htmlFor="name" error={errors.name} required={true}>
        <Input
          id="name"
          value={values.name ?? ''}
          maxLength={255}
          onChange={(v) => set('name', v)}
        />
      </FormField>
      <FormField label="Description" htmlFor="description" error={errors.description} required={false}>
        <TextArea
          id="description"
          value={values.description ?? ''}
          maxLength={4000}
          rows={4}
          onChange={(v) => set('description', v)}
        />
      </FormField>
      <FormField label="Status" htmlFor="status" error={errors.status} required={true}>
        <Select
          id="status"
          value={values.status ?? ''}
          options={LIFECYCLE_OPTIONS}
          allowEmpty={false}
          onChange={(v) => set('status', (v || undefined) as LifecycleStatus | undefined)}
        />
      </FormField>
      <FormField label="External Ref" htmlFor="externalRef" error={errors.externalRef} required={false}>
        <Input
          id="externalRef"
          value={values.externalRef ?? ''}
          maxLength={255}
          onChange={(v) => set('externalRef', v)}
        />
      </FormField>
      <FormField label="Rule Code" htmlFor="ruleCode" error={errors.ruleCode} required={true}>
        <Input
          id="ruleCode"
          value={values.ruleCode ?? ''}
          maxLength={255}
          onChange={(v) => set('ruleCode', v)}
        />
      </FormField>
      <FormField label="Framework" htmlFor="framework" error={errors.framework} required={false}>
        <Select
          id="framework"
          value={values.framework ?? ''}
          options={COMPLIANCE_RULE_FRAMEWORK_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('framework', (v || undefined) as ComplianceRuleFramework | undefined)}
        />
      </FormField>
      <FormField label="Severity" htmlFor="severity" error={errors.severity} required={false}>
        <Select
          id="severity"
          value={values.severity ?? ''}
          options={COMPLIANCE_RULE_SEVERITY_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('severity', (v || undefined) as ComplianceRuleSeverity | undefined)}
        />
      </FormField>
      <FormField label="Expression" htmlFor="expression" error={errors.expression} required={false}>
        <TextArea
          id="expression"
          value={values.expression ?? ''}
          maxLength={4000}
          rows={4}
          onChange={(v) => set('expression', v)}
        />
      </FormField>
      <FormField label="Region" htmlFor="region" error={errors.region} required={false}>
        <Input
          id="region"
          value={values.region ?? ''}
          maxLength={255}
          onChange={(v) => set('region', v)}
        />
      </FormField>
      <FormField label="Owner" htmlFor="owner" error={errors.owner} required={false}>
        <Input
          id="owner"
          value={values.owner ?? ''}
          maxLength={255}
          onChange={(v) => set('owner', v)}
        />
      </FormField>
      <FormField label="Criticality" htmlFor="criticality" error={errors.criticality} required={false}>
        <Select
          id="criticality"
          value={values.criticality ?? ''}
          options={COMPLIANCE_RULE_CRITICALITY_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('criticality', (v || undefined) as ComplianceRuleCriticality | undefined)}
        />
      </FormField>
      <FormField label="Cost Center" htmlFor="costCenter" error={errors.costCenter} required={false}>
        <Input
          id="costCenter"
          value={values.costCenter ?? ''}
          maxLength={255}
          onChange={(v) => set('costCenter', v)}
        />
      </FormField>
      <FormField label="Notes" htmlFor="notes" error={errors.notes} required={false}>
        <TextArea
          id="notes"
          value={values.notes ?? ''}
          maxLength={4000}
          rows={4}
          onChange={(v) => set('notes', v)}
        />
      </FormField>
      <div className="entity-form__actions">
        {onCancel ? (
          <Button type="button" variant="ghost" onClick={onCancel}>
            Cancel
          </Button>
        ) : null}
        <Button type="submit" disabled={busy}>
          {busy ? 'Saving…' : submitLabel}
        </Button>
      </div>
    </form>
  );
}
