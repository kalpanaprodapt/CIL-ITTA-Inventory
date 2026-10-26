import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { RECONCILIATION_RULE_ACTION_OPTIONS, RECONCILIATION_RULE_LIFECYCLE_STAGE_OPTIONS, ReconciliationRuleAction, ReconciliationRuleLifecycleStage } from './types';
import { validateReconciliationRule, cleanReconciliationRuleValues } from './schema';
import { RECONCILIATIONRULE_DEFAULTS } from './types';
import type { ReconciliationRuleFormValues, ReconciliationRuleInput } from './types';

interface Props {
  initial?: ReconciliationRuleFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: ReconciliationRuleInput) => void;
  onCancel?: () => void;
}

export function ReconciliationRuleForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<ReconciliationRuleFormValues>({ ...RECONCILIATIONRULE_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof ReconciliationRuleFormValues>(key: K, value: ReconciliationRuleFormValues[K]) {
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
    const result = validateReconciliationRule(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanReconciliationRuleValues(values as Record<string, unknown>) as unknown as ReconciliationRuleInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Reconciliation Rule form">
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
      <FormField label="Match Key" htmlFor="matchKey" error={errors.matchKey} required={true}>
        <Input
          id="matchKey"
          value={values.matchKey ?? ''}
          maxLength={255}
          onChange={(v) => set('matchKey', v)}
        />
      </FormField>
      <FormField label="Action" htmlFor="action" error={errors.action} required={false}>
        <Select
          id="action"
          value={values.action ?? ''}
          options={RECONCILIATION_RULE_ACTION_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('action', (v || undefined) as ReconciliationRuleAction | undefined)}
        />
      </FormField>
      <FormField label="Priority" htmlFor="priority" error={errors.priority} required={true}>
        <NumberInput
          id="priority"
          value={values.priority}
          step={1}
          onChange={(v) => set('priority', v)}
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
      <FormField label="Labels" htmlFor="labels" error={errors.labels} required={false}>
        <Input
          id="labels"
          value={values.labels ?? ''}
          maxLength={255}
          onChange={(v) => set('labels', v)}
        />
      </FormField>
      <FormField label="Lifecycle Stage" htmlFor="lifecycleStage" error={errors.lifecycleStage} required={false}>
        <Select
          id="lifecycleStage"
          value={values.lifecycleStage ?? ''}
          options={RECONCILIATION_RULE_LIFECYCLE_STAGE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('lifecycleStage', (v || undefined) as ReconciliationRuleLifecycleStage | undefined)}
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
