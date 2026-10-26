import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { COMPLIANCE_CHECK_RESULT_OPTIONS, COMPLIANCE_CHECK_CRITICALITY_OPTIONS, ComplianceCheckResult, ComplianceCheckCriticality } from './types';
import { validateComplianceCheck, cleanComplianceCheckValues } from './schema';
import { COMPLIANCECHECK_DEFAULTS } from './types';
import type { ComplianceCheckFormValues, ComplianceCheckInput } from './types';

interface Props {
  initial?: ComplianceCheckFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: ComplianceCheckInput) => void;
  onCancel?: () => void;
}

export function ComplianceCheckForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<ComplianceCheckFormValues>({ ...COMPLIANCECHECK_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof ComplianceCheckFormValues>(key: K, value: ComplianceCheckFormValues[K]) {
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
    const result = validateComplianceCheck(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanComplianceCheckValues(values as Record<string, unknown>) as unknown as ComplianceCheckInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Compliance Check form">
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
      <FormField label="Rule Id" htmlFor="ruleId" error={errors.ruleId} required={true}>
        <Input
          id="ruleId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.ruleId ?? ''}
          onChange={(v) => set('ruleId', v)}
        />
      </FormField>
      <FormField label="Target Ref" htmlFor="targetRef" error={errors.targetRef} required={true}>
        <Input
          id="targetRef"
          value={values.targetRef ?? ''}
          maxLength={255}
          onChange={(v) => set('targetRef', v)}
        />
      </FormField>
      <FormField label="Checked At" htmlFor="checkedAt" error={errors.checkedAt} required={true}>
        <DateTimeInput
          id="checkedAt"
          value={values.checkedAt ?? ''}
          onChange={(v) => set('checkedAt', v)}
        />
      </FormField>
      <FormField label="Result" htmlFor="result" error={errors.result} required={false}>
        <Select
          id="result"
          value={values.result ?? ''}
          options={COMPLIANCE_CHECK_RESULT_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('result', (v || undefined) as ComplianceCheckResult | undefined)}
        />
      </FormField>
      <FormField label="Evidence" htmlFor="evidence" error={errors.evidence} required={false}>
        <TextArea
          id="evidence"
          value={values.evidence ?? ''}
          maxLength={4000}
          rows={4}
          onChange={(v) => set('evidence', v)}
        />
      </FormField>
      <FormField label="Criticality" htmlFor="criticality" error={errors.criticality} required={false}>
        <Select
          id="criticality"
          value={values.criticality ?? ''}
          options={COMPLIANCE_CHECK_CRITICALITY_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('criticality', (v || undefined) as ComplianceCheckCriticality | undefined)}
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
      <FormField label="Effective From" htmlFor="effectiveFrom" error={errors.effectiveFrom} required={false}>
        <DateInput
          id="effectiveFrom"
          value={values.effectiveFrom ?? ''}
          onChange={(v) => set('effectiveFrom', v)}
        />
      </FormField>
      <FormField label="Effective To" htmlFor="effectiveTo" error={errors.effectiveTo} required={false}>
        <DateInput
          id="effectiveTo"
          value={values.effectiveTo ?? ''}
          onChange={(v) => set('effectiveTo', v)}
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
