import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { SUPPRESSION_RULE_CRITICALITY_OPTIONS, SuppressionRuleCriticality } from './types';
import { validateSuppressionRule, cleanSuppressionRuleValues } from './schema';
import { SUPPRESSIONRULE_DEFAULTS } from './types';
import type { SuppressionRuleFormValues, SuppressionRuleInput } from './types';

interface Props {
  initial?: SuppressionRuleFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: SuppressionRuleInput) => void;
  onCancel?: () => void;
}

export function SuppressionRuleForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<SuppressionRuleFormValues>({ ...SUPPRESSIONRULE_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof SuppressionRuleFormValues>(key: K, value: SuppressionRuleFormValues[K]) {
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
    const result = validateSuppressionRule(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanSuppressionRuleValues(values as Record<string, unknown>) as unknown as SuppressionRuleInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Suppression Rule form">
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
      <FormField label="Rule Name" htmlFor="ruleName" error={errors.ruleName} required={true}>
        <Input
          id="ruleName"
          value={values.ruleName ?? ''}
          maxLength={255}
          onChange={(v) => set('ruleName', v)}
        />
      </FormField>
      <FormField label="Match Expression" htmlFor="matchExpression" error={errors.matchExpression} required={true}>
        <TextArea
          id="matchExpression"
          value={values.matchExpression ?? ''}
          maxLength={4000}
          rows={4}
          onChange={(v) => set('matchExpression', v)}
        />
      </FormField>
      <FormField label="Starts At" htmlFor="startsAt" error={errors.startsAt} required={false}>
        <DateTimeInput
          id="startsAt"
          value={values.startsAt ?? ''}
          onChange={(v) => set('startsAt', v)}
        />
      </FormField>
      <FormField label="Ends At" htmlFor="endsAt" error={errors.endsAt} required={false}>
        <DateTimeInput
          id="endsAt"
          value={values.endsAt ?? ''}
          onChange={(v) => set('endsAt', v)}
        />
      </FormField>
      <FormField label="Reason" htmlFor="reason" error={errors.reason} required={false}>
        <Input
          id="reason"
          value={values.reason ?? ''}
          maxLength={255}
          onChange={(v) => set('reason', v)}
        />
      </FormField>
      <FormField label="Source System" htmlFor="sourceSystem" error={errors.sourceSystem} required={false}>
        <Input
          id="sourceSystem"
          value={values.sourceSystem ?? ''}
          maxLength={255}
          onChange={(v) => set('sourceSystem', v)}
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
          options={SUPPRESSION_RULE_CRITICALITY_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('criticality', (v || undefined) as SuppressionRuleCriticality | undefined)}
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
