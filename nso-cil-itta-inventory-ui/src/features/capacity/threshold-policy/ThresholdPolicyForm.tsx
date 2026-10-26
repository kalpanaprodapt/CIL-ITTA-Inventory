import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { THRESHOLD_POLICY_CRITICALITY_OPTIONS, ThresholdPolicyCriticality } from './types';
import { validateThresholdPolicy, cleanThresholdPolicyValues } from './schema';
import { THRESHOLDPOLICY_DEFAULTS } from './types';
import type { ThresholdPolicyFormValues, ThresholdPolicyInput } from './types';

interface Props {
  initial?: ThresholdPolicyFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: ThresholdPolicyInput) => void;
  onCancel?: () => void;
}

export function ThresholdPolicyForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<ThresholdPolicyFormValues>({ ...THRESHOLDPOLICY_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof ThresholdPolicyFormValues>(key: K, value: ThresholdPolicyFormValues[K]) {
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
    const result = validateThresholdPolicy(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanThresholdPolicyValues(values as Record<string, unknown>) as unknown as ThresholdPolicyInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Threshold Policy form">
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
      <FormField label="Policy Name" htmlFor="policyName" error={errors.policyName} required={true}>
        <Input
          id="policyName"
          value={values.policyName ?? ''}
          maxLength={255}
          onChange={(v) => set('policyName', v)}
        />
      </FormField>
      <FormField label="Metric" htmlFor="metric" error={errors.metric} required={true}>
        <Input
          id="metric"
          value={values.metric ?? ''}
          maxLength={255}
          onChange={(v) => set('metric', v)}
        />
      </FormField>
      <FormField label="Warn Pct" htmlFor="warnPct" error={errors.warnPct} required={true}>
        <NumberInput
          id="warnPct"
          value={values.warnPct}
          step={0.0001}
          onChange={(v) => set('warnPct', v)}
        />
      </FormField>
      <FormField label="Crit Pct" htmlFor="critPct" error={errors.critPct} required={true}>
        <NumberInput
          id="critPct"
          value={values.critPct}
          step={0.0001}
          onChange={(v) => set('critPct', v)}
        />
      </FormField>
      <FormField label="Evaluation Window Minutes" htmlFor="evaluationWindowMinutes" error={errors.evaluationWindowMinutes} required={false}>
        <NumberInput
          id="evaluationWindowMinutes"
          value={values.evaluationWindowMinutes}
          step={1}
          onChange={(v) => set('evaluationWindowMinutes', v)}
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
          options={THRESHOLD_POLICY_CRITICALITY_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('criticality', (v || undefined) as ThresholdPolicyCriticality | undefined)}
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
